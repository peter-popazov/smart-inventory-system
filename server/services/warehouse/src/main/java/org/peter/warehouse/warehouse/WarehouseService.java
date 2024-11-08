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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final LocationRepository locationRepository;

    public ServerResponse<Integer> createWarehouse(CreateWarehouseRequest request,
                                                   String userId, String teamAdminId) {
        Integer adminId = Integer.parseInt(!Objects.equals(teamAdminId, "") ? teamAdminId : userId);

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
                .userId(adminId)
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

    public WarehouseResponse getWarehouseById(Integer id, String userId) {
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

    public Warehouse updateWarehouse(UpdateWarehouseRequest request, String userId) {
        Warehouse warehouse = warehouseRepository.findById(request.warehouseId())
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));

        if (warehouse.getUserId() != Integer.parseInt(userId)) {
            throw new WarehouseNotFoundException("User not found");
        }

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

    public ServerResponse<Boolean> existsById(Integer id, String userId) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
        return ServerResponse.<Boolean>builder().response(warehouse.getUserId() == Integer.parseInt(userId)).build();
    }

    public List<WarehouseResponse> getAllWarehouses(String loggedInUserId, String teamAdminId) {
        Integer adminId = Integer.parseInt(!Objects.equals(teamAdminId, "") ? teamAdminId : loggedInUserId);

        return warehouseRepository.findAllByUserId(adminId).stream()
                .map(WarehousesMapper::toResponse)
                .toList();
    }

}
