package org.peter.notification.kafka;

public record EmailConfirmation(

        String userEmail,

        String payload


) {
    @Override
    public String toString() {
        return "Confirming email for user: " + userEmail;
    }
}
