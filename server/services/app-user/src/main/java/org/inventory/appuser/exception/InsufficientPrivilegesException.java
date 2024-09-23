package org.inventory.appuser.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InsufficientPrivilegesException extends RuntimeException {

    private final String msg;

    public InsufficientPrivilegesException(String string) {
        this.msg = string;
    }
}
