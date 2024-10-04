package org.peter.notification.email;

import lombok.Getter;

public enum EmailTemplates {

    EMAIL_CONFIRMATION("email-confirmation.html", "Email confirmation"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation");

    @Getter
    private final String templateName;

    @Getter
    private final String subject;

    EmailTemplates(String templateName, String subject) {
        this.templateName = templateName;
        this.subject = subject;
    }
}
