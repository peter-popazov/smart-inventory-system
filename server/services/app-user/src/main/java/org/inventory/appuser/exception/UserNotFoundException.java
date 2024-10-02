package org.inventory.appuser.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserNotFoundException extends RuntimeException {

    private final String msg;

    public UserNotFoundException(String string) {
        this.msg = string;
    }
}
