package org.strawberries.orderevents;

import java.util.List;
import java.util.UUID;

public sealed interface OrderEvent {
    record Created(
        UUID id,
        UUID userId,
        String address,
        List<Item> items
    ) implements OrderEvent {}

    record Cancelled(
        UUID id,
        UUID userId,
        List<Item> items
    ) implements OrderEvent {}
}
