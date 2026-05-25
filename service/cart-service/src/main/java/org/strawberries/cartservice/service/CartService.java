package org.strawberries.cartservice.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.accept.ApiVersionStrategy;
import org.strawberries.cartservice.entity.CartItem;
import org.strawberries.cartservice.entity.UserCart;
import org.strawberries.cartservice.mapper.CartItemMapper;
import org.strawberries.cartservice.mapper.CartMapper;
import org.strawberries.cartservice.repository.CartRepository;
import org.strawberries.dto.AddItemRequest;
import org.strawberries.dto.CartResponse;
import org.strawberries.dto.DeleteItemRequest;
import org.strawberries.exception.EmptyCartException;
import org.strawberries.grpc.GetPriceRequest;
import org.strawberries.grpc.GetPriceResponse;
import org.strawberries.grpc.GetPriceServiceGrpc;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository repository;
    private final CartItemMapper cartItemMapper;
    private final CartMapper cartMapper;
    private final GetPriceServiceGrpc.GetPriceServiceBlockingStub getPriceStub;

    public CartResponse createNew(UUID userId) {
        UserCart cart = UserCart.builder()
                .userId(userId)
                .items(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();
        return cartMapper.toRestResponse(repository.save(cart));
    }

    protected UserCart getUserCart(UUID userId) {
        Optional<UserCart> optionalUserCart = repository.findById(userId);
        if (optionalUserCart.isEmpty()) throw new NoSuchElementException(String.format("Cart for user with id=%s not found", userId));
        return optionalUserCart.get();
    }

    public CartResponse addItem(AddItemRequest request) {
        UserCart cart = getUserCart(request.userId());
        List<CartItem> items = cart.getItems();
        CartItem newItem = cartItemMapper.toEntityFromRestCreate(request);

        GetPriceRequest getPriceRequest = GetPriceRequest.newBuilder()
                .setProductId(String.valueOf(request.productId()))
                .build();

        GetPriceResponse getPriceResponse = getPriceStub.getPrice(getPriceRequest);

        BigDecimal price = new BigDecimal(getPriceResponse.getPrice());
        BigDecimal discountAmount = price.multiply(BigDecimal.valueOf(getPriceResponse.getDiscount()).divide(BigDecimal.valueOf(100)));
        BigDecimal priceWithDiscount = price.subtract(discountAmount);

        newItem.setProductPrice(price);
        newItem.setDiscount(getPriceResponse.getDiscount());
        newItem.setPriceWithDiscount(priceWithDiscount);

        items.add(newItem);
        return cartMapper.toRestResponse(repository.save(cart));
    }

    public CartResponse deleteItem(DeleteItemRequest request) {
        UserCart cart = getUserCart(request.userId());
        List<CartItem> items = cart.getItems();
        if (items.isEmpty()) throw new EmptyCartException(request.userId());
        cart.setItems(items.stream().filter(item -> !item.getProductId().equals(request.productId())).toList());
        return cartMapper.toRestResponse(repository.save(cart));
    }

    public CartResponse getCart(UUID userId) {
        UserCart cart = getUserCart(userId);
        return cartMapper.toRestResponse(cart);
    }

    public CartResponse clearCart(UUID userId) {
        repository.deleteById(userId);
        return CartResponse.builder().build();
    }
}
