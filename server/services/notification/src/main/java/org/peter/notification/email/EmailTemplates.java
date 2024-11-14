package org.peter.notification.email;

import lombok.Getter;

@Getter
public enum EmailTemplates {

    EMAIL_CONFIRMATION("email-confirmation.html", "Email confirmation"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation"),
    LOW_STOCK_ALERT("low-stock-alert.html", "Low stock Alert");

    private final String templateName;

    private final String subject;

    EmailTemplates(String templateName, String subject) {
        this.templateName = templateName;
        this.subject = subject;
    }
}
