package org.peter.warehouse.warehouse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {

    @GetMapping("/api/v1/products/warehouses")
    List<Integer> getWarehousesByUserId(@RequestParam("userId") String userId);
}