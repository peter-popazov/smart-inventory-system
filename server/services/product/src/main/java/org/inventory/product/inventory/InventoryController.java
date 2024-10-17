package org.inventory.product.inventory;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.dto.InventoryResponse;
import org.inventory.product.dto.UpdateInventoryRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}/inventory")
    public ResponseEntity<List<InventoryResponse>> getInventory(@PathVariable Integer productId,
                                                                @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(inventoryService.getInventoryForProduct(productId, loggedInUserId), HttpStatus.OK);
    }

    @PutMapping("/{productId}/inventory")
    public ResponseEntity<ServerResponse<String>> updateInventoryForProduct(@PathVariable Integer productId,
                                                                            @RequestBody UpdateInventoryRequest request,
                                                                            @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(inventoryService.updateInventoryForProduct(productId, request, loggedInUserId), HttpStatus.OK);
    }
}
