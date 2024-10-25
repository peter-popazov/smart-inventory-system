package org.peter.order.exception;

import lombok.Builder;

import java.util.Map;

@Builder
public record ErrorResponse(
        Map<String, String> errors,
        String businessError
) {
}
