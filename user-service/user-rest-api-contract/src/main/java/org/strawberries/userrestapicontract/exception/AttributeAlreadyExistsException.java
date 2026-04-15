package org.strawberries.userrestapicontract.exception;

public class AttributeAlreadyExistsException extends RuntimeException {
    public AttributeAlreadyExistsException(String attributeName, Object attributeValue) {
        super(String.format("User with %s %s already exists", attributeName, attributeValue));
    }
}
