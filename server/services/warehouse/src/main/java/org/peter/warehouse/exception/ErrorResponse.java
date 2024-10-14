package org.peter.warehouse.exception;

import lombok.Builder;

import java.util.Map;

@Builder
public record ErrorResponse(
        Map<String, String> errors,
        String businessErrorDesc,
        String error
) {
}
