package org.peter.notification.kafka;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.peter.notification.email.EmailService;
import org.peter.notification.message.Message;
import org.peter.notification.repository.NotificationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;

    private final EmailService emailService;

    @KafkaListener(topics = "email-confirmation-topic")
    public void consumeEmailConfirmationNotification(EmailConfirmation emailConfirmation) {
        log.info("Consuming message from email confirmation topic. Topic:: %s ".formatted(emailConfirmation.toString()));
        repository.save(Message.builder()
                .emailConfirmation(emailConfirmation)
                .notificationTime(LocalDateTime.now())
                .build()
        );

        try {
            emailService.sendEmailConfirmation(emailConfirmation.userEmail(), emailConfirmation.payload());
        } catch (MessagingException e) {
            log.warn("Email confirmation failed to send to {}", emailConfirmation.userEmail(), e);
        }
    }

    @KafkaListener(topics = "email-low-stock-topic")
    public void consumeLowStockEmail(LowStockProduct lowStockProduct) {
        log.info("Consuming message from email low stock topic. Topic:: %s ".formatted(lowStockProduct.toString()));
        repository.save(Message.builder()
                .lowStockProduct(lowStockProduct)
                .notificationTime(LocalDateTime.now())
                .build()
        );

        try {
            emailService.sendEmailLowStock(lowStockProduct);
        } catch (MessagingException e) {
            log.warn("Email confirmation failed to send to {}", lowStockProduct.userEmail(), e);
        }
    }
}

