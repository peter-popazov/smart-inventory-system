package org.peter.customer.kafka;

import lombok.Builder;

@Builder
public record CustomerRegisteredRequest(

        String userEmail,
        Integer userId
) {

    @Override
    public String toString() {
        return "User registered with email: " + userEmail + ", id: " + userId;
    }
}
