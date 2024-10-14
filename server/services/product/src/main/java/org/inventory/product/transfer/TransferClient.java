package org.inventory.product.transfer;

import org.inventory.product.dto.TransferRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "transfers", url = "${warehouse.service.url}")
public interface TransferClient {

    @PostMapping("/api/v1/transfers")
    void transferProduct(@RequestBody TransferRequest transferRequest);
}
