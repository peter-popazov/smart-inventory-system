package org.peter.auth.kafka;

import lombok.Builder;

@Builder
public record RegisteredRequest(

        String userEmail,
        Integer userId
) {
}
