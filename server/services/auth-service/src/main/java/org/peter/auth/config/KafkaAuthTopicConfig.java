package org.peter.auth.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaAuthTopicConfig {

    @Bean
    public NewTopic emailConfirmationTopic() {
        return TopicBuilder.name("email-confirmation-topic").build();
    }
}
