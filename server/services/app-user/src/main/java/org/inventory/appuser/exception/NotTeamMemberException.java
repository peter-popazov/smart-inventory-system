package org.inventory.appuser.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class NotTeamMemberException extends RuntimeException {

    private final String msg;

    public NotTeamMemberException(String string) {
        this.msg = string;
    }
}
