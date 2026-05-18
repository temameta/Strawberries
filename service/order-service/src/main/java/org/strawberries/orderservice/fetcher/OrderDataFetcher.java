package org.strawberries.orderservice.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.strawberries.orderapi.codegen.types.*;
import org.strawberries.orderservice.service.OrderService;

import java.util.Objects;
import java.util.UUID;

@DgsComponent
@RequiredArgsConstructor
public class OrderDataFetcher {
    private final OrderService service;

    @DgsQuery
    public Order order(@InputArgument UUID id) {
        return service.findById(id);
    }

    @DgsQuery
    public OrderCollection orders(@InputArgument Integer page,
                                  @InputArgument Integer size) {
        page = Objects.requireNonNullElse(page, 0);
        size = Objects.requireNonNullElse(size, 20);
        return service.findAll(page, size);
    }

    @DgsMutation
    public Order createOrder(@InputArgument CreateOrder input) {
        return service.createOrder(input);
    }

    @DgsMutation
    public Order updateStatus(@InputArgument UpdateStatus input) {
        return service.updateStatus(input);
    }

    @DgsMutation
    public Order cancelOrder(@InputArgument CancelOrder input) {
        return service.cancelOrder(input);
    }
}
