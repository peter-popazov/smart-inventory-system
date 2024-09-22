package org.inventory.appuser.user.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserNotFoundException extends RuntimeException {

    private final String msg;
    {
        msg = "User not found";
    }
    public UserNotFoundException(String string) {
    }
}
