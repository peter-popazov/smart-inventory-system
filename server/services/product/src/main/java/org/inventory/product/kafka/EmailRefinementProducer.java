package org.inventory.product.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inventory.product.refinement.LowStockProduct;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailRefinementProducer {

    private final KafkaTemplate<String, LowStockProduct> kafkaTemplate;

    public void sendRefinementEmail(LowStockProduct lowStockProduct) {
        log.info("Sending email to low stock product");
        Message<LowStockProduct> message = MessageBuilder
                .withPayload(lowStockProduct)
                .setHeader(KafkaHeaders.TOPIC, "email-low-stock-topic")
                .build();

        kafkaTemplate.send(message);
    }
}
