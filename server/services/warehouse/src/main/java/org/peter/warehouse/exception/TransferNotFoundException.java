package org.peter.warehouse.exception;

public class TransferNotFoundException extends RuntimeException {

    public TransferNotFoundException(String message) {
        super(message);
    }
}
