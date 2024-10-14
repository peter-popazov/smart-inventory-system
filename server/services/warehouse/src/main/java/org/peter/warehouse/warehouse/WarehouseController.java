package org.peter.warehouse.warehouse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.peter.warehouse.helpers.ServerResponse;
import org.peter.warehouse.dto.CreateWarehouseRequest;
import org.peter.warehouse.dto.UpdateWarehouseRequest;
import org.peter.warehouse.dto.WarehouseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<ServerResponse<Integer>> createWarehouse(@RequestBody CreateWarehouseRequest request) {
        return new ResponseEntity<>(warehouseService.createWarehouse(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(@PathVariable Integer id) {
        return new ResponseEntity<>(warehouseService.getWarehouseById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouses(@RequestHeader("loggedInUserId") String loggedInUserId) {
        List<WarehouseResponse> warehouses = warehouseService.getAllWarehouses(loggedInUserId);
        return new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<ServerResponse<Boolean>> existsWarehouse(@PathVariable Integer id) {
        return new ResponseEntity<>(warehouseService.existsById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(
            @PathVariable Integer id,
            @RequestBody @Valid UpdateWarehouseRequest request) {
        return new ResponseEntity<>(warehouseService.updateWarehouse(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServerResponse<Void>> deleteWarehouse(@PathVariable Integer id) {
        warehouseService.deleteWarehouse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
