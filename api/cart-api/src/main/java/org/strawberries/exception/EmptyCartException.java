package org.strawberries.exception;

import java.util.UUID;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException(UUID userId) {
        super(String.format("Cart of user with id=%s is empty", userId));
    }
}
