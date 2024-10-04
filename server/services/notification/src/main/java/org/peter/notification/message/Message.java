package org.peter.notification.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.peter.notification.kafka.EmailConfirmation;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Message {

    @Id
    private String id;

    private NotificationType notification;

    private LocalDateTime notificationTime;

    private EmailConfirmation emailConfirmation;
}
