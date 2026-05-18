package org.strawberries.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.strawberries.orderapi.codegen.types.*;
import org.strawberries.orderservice.entity.OrderEntity;
import org.strawberries.orderservice.mapper.ItemMapper;
import org.strawberries.orderservice.mapper.OrderMapper;
import org.strawberries.orderservice.repository.OrderRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final ItemMapper itemMapper;

    @Transactional
    public Order createOrder(CreateOrder input) {
        OrderEntity newOrder = mapper.toEntityFromCreate(input);
        newOrder.setStatus(OrderStatus.CREATED);
        newOrder.setTimestamp(OffsetDateTime.now());
        newOrder.setItems(input.getItems().stream()
                .map(itemMapper::toEntityFromCreate)
                .toList());
        return mapper.toGqlType(repository.save(newOrder));
    }

    @Transactional
    public Order updateStatus(UpdateStatus input) {
        OrderEntity orderEntity = getOrder(input.getOrderId());
        orderEntity.setStatus(input.getNewStatus());
        return mapper.toGqlType(repository.save(orderEntity));
    }

    @Transactional
    public Order cancelOrder(CancelOrder input) {
        OrderEntity orderEntity = getOrder(input.getOrderId());
        orderEntity.setStatus(OrderStatus.CANCELLED);
        return mapper.toGqlType(repository.save(orderEntity));
    }

    protected OrderEntity getOrder(UUID id) {
        Optional<OrderEntity> order = repository.findById(id);
        if (order.isEmpty()) throw new NoSuchElementException(String.format("Order with id=%s not found", id));
        return order.get();
    }

    @Transactional(readOnly = true)
    public Order findById(UUID id) {
        return mapper.toGqlType(getOrder(id));
    }

    @Transactional(readOnly = true)
    public OrderCollection findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> ordersPaged = repository.findAll(pageable);
        List<Order> content = ordersPaged.getContent().stream()
                .map(mapper::toGqlType)
                .toList();
        PageInfo pageInfo = PageInfo.newBuilder()
                .last(ordersPaged.isLast())
                .pageNumber(ordersPaged.getNumber())
                .pageSize(ordersPaged.getSize())
                .totalPages(ordersPaged.getTotalPages())
                .build();
        return OrderCollection.newBuilder()
                .content(content)
                .pageInfo(pageInfo)
                .totalElements(ordersPaged.getTotalElements())
                .build();
    }
}
