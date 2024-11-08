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
    public ResponseEntity<ServerResponse<Integer>> createWarehouse(@RequestBody CreateWarehouseRequest request,
                                                                   @RequestHeader("loggedInUserId") String loggedInUserId,
                                                                   @RequestHeader("teamAdminId") String teamAdminId) {
        return new ResponseEntity<>(warehouseService.createWarehouse(request, loggedInUserId, teamAdminId), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(@PathVariable Integer id,
                                                              @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(warehouseService.getWarehouseById(id, loggedInUserId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouses(@RequestHeader("loggedInUserId") String loggedInUserId,
                                                                    @RequestHeader("teamAdminId") String teamAdminId) {
        return new ResponseEntity<>(warehouseService.getAllWarehouses(loggedInUserId, teamAdminId), HttpStatus.OK);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<ServerResponse<Boolean>> existsWarehouse(@PathVariable Integer id,
                                                                   @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(warehouseService.existsById(id, loggedInUserId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Warehouse> updateWarehouse(
            @RequestHeader("loggedInUserId") String loggedInUserId,
            @RequestBody @Valid UpdateWarehouseRequest request) {
        return new ResponseEntity<>(warehouseService.updateWarehouse(request, loggedInUserId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServerResponse<Void>> deleteWarehouse(@PathVariable Integer id) {
        warehouseService.deleteWarehouse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/internal/{id}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(@PathVariable Integer id) {
        return new ResponseEntity<>(warehouseService.getWarehouseById(id), HttpStatus.OK);
    }
}
