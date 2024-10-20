package org.peter.customer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.peter.customer.customer.CustomerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {

    private final CustomerService service;

    @KafkaListener(topics = "customer-registered-topic")
    public void consumeEmailConfirmationNotification(CustomerRegisteredRequest request) {
        service.registerCustomer(request.userId(), request.userEmail());
        log.info("Customer registered");
    }
}
