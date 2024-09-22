package org.inventory.appuser.user.exception;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
