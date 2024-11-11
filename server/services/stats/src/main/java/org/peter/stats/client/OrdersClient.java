package org.peter.stats.client;

import org.peter.stats.ProductStats;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;

@FeignClient(name = "order-server", url = "${application.order-server.url}")
public interface OrdersClient {

    @GetMapping("/stats")
    BigDecimal getOrdersStats(@RequestHeader("userId") String userId);
}