package org.peter.auth.kafka;

import lombok.Builder;

@Builder
public record Email(

        String userEmail,

        String payload
) {
}
