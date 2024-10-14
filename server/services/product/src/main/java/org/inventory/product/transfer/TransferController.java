package org.inventory.product.transfer;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.dto.TransferRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<ServerResponse<String>> transferProduct(@RequestBody TransferRequest transferRequest) {
        return new ResponseEntity<>(transferService.transferProduct(transferRequest), HttpStatus.CREATED);
    }
}
