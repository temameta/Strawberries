package org.strawberries.cartservice.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.strawberries.cartservice.entity.UserCart;
import org.strawberries.cartservice.mapper.CartItemMapper;
import org.strawberries.cartservice.repository.CartRepository;
import org.strawberries.grpc.CartRequest;
import org.strawberries.grpc.CartResponse;
import org.strawberries.grpc.CartServiceGrpc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GetCartServiceImpl extends CartServiceGrpc.CartServiceImplBase {
    private final CartRepository repository;
    private final CartItemMapper cartItemMapper;

    @Override
    public void getCart(CartRequest request, StreamObserver<CartResponse> responseObserver) {
        UUID userId;
        try {
            userId = UUID.fromString(request.getUserId());
        } catch (IllegalArgumentException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Invalid UUID format: " + request.getUserId())
                    .asRuntimeException());
            return;
        }
        Optional<UserCart> optionalUserCart = repository.findById(userId);
        if (optionalUserCart.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Cart for user " + userId + " not found")
                    .asRuntimeException());
            return;
        }
        UserCart cart = optionalUserCart.get();
        List<CartResponse.CartItem> items = cart.getItems().stream()
                .map(cartItemMapper::toGrpcResponse)
                .toList();
        BigDecimal totalPrice = cart.getItems().stream()
                .map(i -> i.getPriceWithDiscount().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        CartResponse response = CartResponse.newBuilder()
                .setTotalPrice(String.valueOf(totalPrice))
                .addAllItems(items)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
