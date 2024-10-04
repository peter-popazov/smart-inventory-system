package org.peter.auth.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailProducer {

    private final KafkaTemplate<String, Email> kafkaTemplate;

    public void sendEmail(Email email) {
        log.info("Sending email");
        Message<Email> message = MessageBuilder
                .withPayload(email)
                .setHeader(KafkaHeaders.TOPIC, "email-confirmation-topic")
                .build();

        kafkaTemplate.send(message);
    }
}
