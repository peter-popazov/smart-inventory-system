package org.inventory.product.movements;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inventory.product.movements.dto.StockMovementsRequest;
import org.inventory.product.movements.dto.StockMovementsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class StockMovementsController {

    private final StockMovementsService stockMovementsService;

    @GetMapping("/{productId}/movements")
    public ResponseEntity<List<StockMovementsResponse>> getMovementsForProduct(@PathVariable("productId") Integer productId) {
        return new ResponseEntity<>(stockMovementsService.getMovementsForProduct(productId), HttpStatus.OK);
    }

    @GetMapping("/movements")
    public ResponseEntity<List<StockMovementsResponse>> getMovements(@RequestHeader("loggedInUserId") String loggedInUserId,
                                                                     @RequestHeader("teamAdminId") String teamAdminId) {
        return new ResponseEntity<>(stockMovementsService.getMovements(loggedInUserId, teamAdminId), HttpStatus.OK);
    }

    @PostMapping("/movements")
    public ResponseEntity<?> addMovementsForProduct(@Valid @RequestBody StockMovementsRequest request) {
        stockMovementsService.addMovementsForProduct(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/movements/all")
    public ResponseEntity<?> findMovementsForProducts(@RequestHeader("loggedInUserId") String loggedInUserId,
                                                    @RequestHeader("teamAdminId") String teamAdminId) {
        return ResponseEntity.ok(stockMovementsService.findStockStatistics(loggedInUserId, teamAdminId));
    }
}
