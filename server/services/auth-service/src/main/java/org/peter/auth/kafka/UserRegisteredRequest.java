package org.peter.auth.kafka;

import lombok.Builder;

@Builder
public record UserRegisteredRequest(

        String userEmail,
        Integer userId
) {
}
