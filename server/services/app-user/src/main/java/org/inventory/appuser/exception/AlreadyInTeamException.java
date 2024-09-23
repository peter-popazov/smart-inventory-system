package org.inventory.appuser.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AlreadyInTeamException extends RuntimeException {

    private final String msg;

    public AlreadyInTeamException(String string) {
        this.msg = string;
    }
}
