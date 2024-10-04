package org.inventory.appuser.kafka;

import lombok.Builder;

@Builder
public record UserRegisteredRequest(

        String userEmail,
        Integer userId
) {
    @Override
    public String toString() {
        return "User registered with email: " + userEmail + ", id: " + userId;
    }
}
