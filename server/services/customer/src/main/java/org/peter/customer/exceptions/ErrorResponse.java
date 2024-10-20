package org.peter.customer.exceptions;

import lombok.Builder;

import java.util.Map;

@Builder
public record ErrorResponse(
        Map<String, String> errors,
        String businessError
) {
}
