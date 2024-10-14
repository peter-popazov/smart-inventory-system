package org.peter.warehouse.transfer;

import lombok.RequiredArgsConstructor;
import org.peter.warehouse.dto.TransferRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<Void> transfer(@RequestBody TransferRequest request) {
        transferService.transferProduct(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferResponse> getTransferById(@PathVariable Integer id) {
        return new ResponseEntity<>(transferService.getTransferById(id), HttpStatus.OK);
    }
}
