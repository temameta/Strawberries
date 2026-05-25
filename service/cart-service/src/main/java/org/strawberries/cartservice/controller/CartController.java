package org.strawberries.cartservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.strawberries.cartservice.service.CartService;
import org.strawberries.dto.AddItemRequest;
import org.strawberries.dto.CartResponse;
import org.strawberries.dto.DeleteItemRequest;
import org.strawberries.endpoints.CartApi;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CartController implements CartApi {
    private final CartService service;

    @Override
    public CartResponse createNew(UUID userId) {
        return service.createNew(userId);
    }

    @Override
    public CartResponse addItem(AddItemRequest request) {
        return service.addItem(request);
    }

    @Override
    public CartResponse deleteItem(DeleteItemRequest request) {
        return service.deleteItem(request);
    }

    @Override
    public CartResponse getCart(UUID userId) {
        return service.getCart(userId);
    }

    @Override
    public CartResponse clearCart(UUID userId) {
        return service.clearCart(userId);
    }
}
