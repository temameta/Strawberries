package org.strawberries.productservice.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.strawberries.grpc.GetPriceRequest;
import org.strawberries.grpc.GetPriceResponse;
import org.strawberries.grpc.GetPriceServiceGrpc;
import org.strawberries.productservice.entity.ProductEntity;
import org.strawberries.productservice.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetPriceServiceImpl extends GetPriceServiceGrpc.GetPriceServiceImplBase {
    private final ProductRepository repository;

    @Override
    public void getPrice(GetPriceRequest request, StreamObserver<GetPriceResponse> responseObserver) {
        UUID productId;
        try {
            productId = UUID.fromString(request.getProductId());
        } catch (IllegalArgumentException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Invalid UUID format: " + request.getProductId())
                    .asRuntimeException());
            return;
        }
        Optional<ProductEntity> optionalProductEntity = repository.findByIdAndActiveTrue(UUID.fromString(request.getProductId()));
        if (optionalProductEntity.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Product with id = " + productId + " not found")
                    .asRuntimeException());
            return;
        }
        ProductEntity product = optionalProductEntity.get();
        GetPriceResponse response = GetPriceResponse.newBuilder()
                .setPrice(String.valueOf(product.getPrice()))
                .setDiscount(product.getDiscount())
                .setProductId(String.valueOf(productId))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
