package org.strawberries.orderevents;

import java.util.UUID;

public sealed interface OrderEvent {
    record Created(
        UUID id,
        UUID userId,
        String address,
        int productsAmount
    ) implements OrderEvent {}

    record Deleted(
        UUID id,
        UUID userId
    ) implements OrderEvent {}
}
