package org.strawberries.orderevents;

import java.time.Instant;
import java.util.UUID;

public record EventMetadata(
        UUID eventId,

        Instant timestamp,

        String source,

        String eventType
) {
    public static EventMetadata create(String source, String eventType) {
        return new EventMetadata(
                UUID.randomUUID(),
                Instant.now(),
                source,
                eventType
        );
    }
}
