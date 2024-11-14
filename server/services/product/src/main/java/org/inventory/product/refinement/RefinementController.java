package org.inventory.product.refinement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products/refill")
@RequiredArgsConstructor
public class RefinementController {

    private final RefillmentService refinementService;

    @GetMapping("/{productId}")
    public ResponseEntity<?> sendRefinementEmail(@PathVariable Integer productId,
                                                 @RequestHeader("loggedInUserId") String userId) {
        refinementService.sendRefinementEmail(productId, userId);
        return ResponseEntity.ok().build();
    }
}
