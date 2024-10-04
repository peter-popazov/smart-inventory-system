package org.peter.notification.repository;

import org.peter.notification.message.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Message, String> {
}
