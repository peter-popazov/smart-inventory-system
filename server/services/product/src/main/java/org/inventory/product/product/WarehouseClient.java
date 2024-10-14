package org.inventory.product.product;

import org.inventory.product.dto.WarehouseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "warehouses", url = "${warehouse.service.url}")
public interface WarehouseClient {

    @GetMapping("/api/v1/warehouses/{id}")
    WarehouseResponse getWarehouseById(@PathVariable("id") Integer id);
}
