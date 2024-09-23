package org.inventory.appuser.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TeamNotFoundException extends RuntimeException {

    private final String msg;

    public TeamNotFoundException(String string) {
        this.msg = string;
    }
}
