package org.peter.order.client;

import org.peter.order.dto.PurchaseProductsRequest;
import org.peter.order.dto.PurchaseProductsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "product-service", url = "${application.config.product-service}")
public interface ProductClient {

    @PostMapping("/purchase")
    ResponseEntity<List<PurchaseProductsResponse>> purchaseProducts(@RequestBody List<PurchaseProductsRequest> requests,
                                                                    @RequestHeader("loggedInUserId") String loggedInUserId);
}