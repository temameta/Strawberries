package org.strawberries.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.strawberries.orderapi.codegen.types.CreateOrder;
import org.strawberries.orderapi.codegen.types.Order;
import org.strawberries.orderapi.codegen.types.OrderStatus;
import org.strawberries.orderservice.entity.OrderEntity;
import org.strawberries.orderservice.mapper.OrderMapper;
import org.strawberries.orderservice.repository.OrderRepository;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    public Order createOrder(CreateOrder input) {
        OrderEntity newOrder = mapper.toEntityFromCreate(input);
        newOrder.setStatus(OrderStatus.CREATED);
        newOrder.setTimeStamp(OffsetDateTime.now());
        List<Product>
    }
}
