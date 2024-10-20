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
public class RegisteredProducer {

    private final KafkaTemplate<String, RegisteredRequest> kafkaTemplate;

    public void userRegistered(RegisteredRequest request) {
        log.info("User registered");
        Message<RegisteredRequest> message = MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.TOPIC, "user-registered-topic")
                .build();

        kafkaTemplate.send(message);
    }

    public void customerRegistered(RegisteredRequest request) {
        log.info("Customer registered");
        Message<RegisteredRequest> message = MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.TOPIC, "customer-registered-topic")
                .build();

        kafkaTemplate.send(message);
    }
}
