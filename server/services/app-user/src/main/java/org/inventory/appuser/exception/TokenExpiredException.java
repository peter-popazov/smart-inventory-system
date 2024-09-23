package org.inventory.appuser.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TokenExpiredException extends RuntimeException {

    private final String msg;

    public TokenExpiredException(String string) {
        this.msg = string;
    }
}
