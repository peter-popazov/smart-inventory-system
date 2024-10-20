package org.peter.customer.exceptions;

public class InsufficientPrivilegesException extends RuntimeException {

    public InsufficientPrivilegesException(String message) {
        super(message);
    }
}
