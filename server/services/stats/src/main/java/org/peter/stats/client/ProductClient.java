package org.peter.stats.client;

import org.peter.stats.ProductStats;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "product-server", url = "${application.product-server.url}")
public interface ProductClient {

    @GetMapping("/stats")
    ProductStats getProductStats(@RequestHeader("userId") String userId);
}