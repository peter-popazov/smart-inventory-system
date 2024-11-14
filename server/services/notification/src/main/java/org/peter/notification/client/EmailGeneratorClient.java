package org.peter.notification.client;


import org.peter.notification.kafka.LowStockProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "email-generator-service", url = "${application.document-generator-server.url}")
public interface EmailGeneratorClient {

    @PostMapping("/order")
    byte[] getOrderPdf(LowStockProduct lowStockProduct);
}