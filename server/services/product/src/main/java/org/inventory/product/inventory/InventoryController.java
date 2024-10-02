package org.inventory.product.inventory;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}/inventory")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable Integer productId) {
        return new ResponseEntity<>(inventoryService.getInventoryForProduct(productId), HttpStatus.OK);
    }

    @PutMapping("/{productId}/inventory")
    public ResponseEntity<ServerResponse<String>> updateInventoryForProduct(@PathVariable Integer productId,
                                                                            @RequestBody UpdateInventoryRequest request){
        return new ResponseEntity<>(inventoryService.updateInventoryForProduct(productId, request), HttpStatus.OK);
    }
}
