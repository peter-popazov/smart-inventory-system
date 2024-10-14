package org.peter.warehouse.warehouse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.peter.warehouse.exception.WarehouseNotFoundException;
import org.peter.warehouse.helpers.ServerResponse;
import org.peter.warehouse.helpers.WarehousesMapper;
import org.peter.warehouse.location.Location;
import org.peter.warehouse.location.LocationRepository;
import org.peter.warehouse.dto.CreateWarehouseRequest;
import org.peter.warehouse.dto.UpdateWarehouseRequest;
import org.peter.warehouse.dto.LocationResponse;
import org.peter.warehouse.dto.WarehouseResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final LocationRepository locationRepository;
    private final ProductClient productClient;

    public ServerResponse<Integer> createWarehouse(CreateWarehouseRequest request) {
        Location location = locationRepository.findByPostalCode(request.postalCode())
                .orElseGet(() -> Location.builder()
                        .city(request.city())
                        .address(request.address())
                        .postalCode(request.postalCode())
                        .build());

        locationRepository.save(location);

        Warehouse warehouse = Warehouse.builder()
                .name(request.name())
                .isRefrigerated(request.isRefrigerated())
                .location(location)
                .build();

        Integer id = warehouseRepository.save(warehouse).getWarehouseId();

        return ServerResponse.<Integer>builder().response(id).build();

    }

    public WarehouseResponse getWarehouseById(Integer id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));

        return WarehouseResponse.builder()
                .warehouseId(warehouse.getWarehouseId())
                .name(warehouse.getName())
                .isRefrigerated(warehouse.isRefrigerated())
                .location(LocationResponse.builder()
                        .city(warehouse.getLocation().getCity())
                        .address(warehouse.getLocation().getAddress())
                        .postalCode(warehouse.getLocation().getPostalCode())
                        .build())
                .build();
    }

    public Warehouse updateWarehouse(Integer id, UpdateWarehouseRequest request) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));

        warehouse.setName(request.name());
        warehouse.setRefrigerated(request.isRefrigerated());

        Location location = locationRepository.findByPostalCode(request.postalCode())
                .orElseGet(() -> Location.builder()
                        .address(request.address())
                        .postalCode(request.postalCode())
                        .build());
        warehouse.setLocation(location);

        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Integer id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));
        warehouseRepository.delete(warehouse);
    }

    public ServerResponse<Boolean> existsById(Integer id) {
        return ServerResponse.<Boolean>builder().response(warehouseRepository.existsById(id)).build();
    }

    public List<WarehouseResponse> getAllWarehouses(String loggedInUserId) {

        List<Integer> warehousesId = productClient.getWarehousesByUserId(loggedInUserId);

        return warehousesId.stream()
                .map(id -> warehouseRepository.findById(id)
                        .map(WarehousesMapper::toResponse)
                        .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found with id: " + id))
                )
                .toList();
    }

}
