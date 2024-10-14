package org.inventory.product.exceptions;

public class InsufficientQuantityException extends RuntimeException {

    public InsufficientQuantityException(String message) {
        super(message);
    }
}
