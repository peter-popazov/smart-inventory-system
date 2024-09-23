package org.inventory.appuser.user.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TokenNotFoundException extends RuntimeException {

    private final String msg;

    public TokenNotFoundException(String string) {
        this.msg = string;
    }
}
