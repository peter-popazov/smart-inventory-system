package org.inventory.product.lowstock;

import lombok.RequiredArgsConstructor;
import org.inventory.product.lowstock.dto.LowStockAlertResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class LowStockAlertController {

    private final LowStockAlertService lowStockAlertService;

    @GetMapping
    public ResponseEntity<List<LowStockAlertResponse>> getLowStockAlerts(@RequestHeader("loggedInUserId") String loggedInUserId,
                                                                         @RequestHeader("teamAdminId") String teamAdminId) {
        return new ResponseEntity<>(lowStockAlertService.getAlerts(loggedInUserId, teamAdminId), HttpStatus.OK);
    }

    @PutMapping("/{alertId}")
    public ResponseEntity<?> getLowStockAlerts(@PathVariable Integer alertId) {
        lowStockAlertService.processAlert(alertId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
