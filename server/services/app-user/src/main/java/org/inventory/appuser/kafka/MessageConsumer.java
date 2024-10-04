package org.inventory.appuser.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inventory.appuser.user.AppUserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {

    private final AppUserService service;

    @KafkaListener(topics = "user-registered-topic")
    public void consumeEmailConfirmationNotification(UserRegisteredRequest request) {
        service.registerUser(request.userId(), request.userEmail());
        log.info("User registered");
    }
}
