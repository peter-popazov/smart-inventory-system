package org.peter.warehouse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.peter.warehouse.dto.CreateWarehouseRequest;
import org.peter.warehouse.dto.UpdateWarehouseRequest;
import org.peter.warehouse.dto.WarehouseResponse;
import org.peter.warehouse.exception.WarehouseNotFoundException;
import org.peter.warehouse.helpers.ServerResponse;
import org.peter.warehouse.location.Location;
import org.peter.warehouse.location.LocationRepository;
import org.peter.warehouse.warehouse.Warehouse;
import org.peter.warehouse.warehouse.WarehouseRepository;
import org.peter.warehouse.warehouse.WarehouseService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WarehouseServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private WarehouseService warehouseService;

    private Location location;
    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        location = Location.builder().locationId(1).address("Address").city("City").postalCode("12345").build();
        warehouse = Warehouse.builder().warehouseId(1).name("Warehouse1").isRefrigerated(true).location(location).userId(1).build();
    }

    @Test
    void shouldCreateWarehouse() {
        CreateWarehouseRequest request = new CreateWarehouseRequest("Warehouse1", true, "City", "Address", "12345");
        String userId = "1";
        String teamAdminId = "";

        when(locationRepository.findByPostalCode(request.postalCode())).thenReturn(Optional.empty());
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);

        ServerResponse<Integer> response = warehouseService.createWarehouse(request, userId, teamAdminId);

        assertNotNull(response);
        assertEquals(1, response.getResponse());
        verify(locationRepository).save(any(Location.class));
        verify(warehouseRepository).save(any(Warehouse.class));
    }

    @Test
    void shouldReturnWarehouseById() {
        Integer warehouseId = 1;
        when(warehouseRepository.findById(warehouseId)).thenReturn(Optional.of(warehouse));

        WarehouseResponse response = warehouseService.getWarehouseById(warehouseId);

        assertNotNull(response);
        assertEquals("Warehouse1", response.name());
        assertEquals("City", response.location().city());
        assertTrue(response.isRefrigerated());
    }

    @Test
    void shouldReturnWarehouseByIdWithUserId() {
        Integer warehouseId = 1;
        String userId = "1";
        when(warehouseRepository.findById(warehouseId)).thenReturn(Optional.of(warehouse));

        WarehouseResponse response = warehouseService.getWarehouseById(warehouseId, userId);

        assertNotNull(response);
        assertEquals("Warehouse1", response.name());
        assertEquals("City", response.location().city());
        assertTrue(response.isRefrigerated());
    }

    @Test
    void shouldUpdateWarehouse() {
        UpdateWarehouseRequest request = new UpdateWarehouseRequest(1, "UpdatedWarehouse", false, "UpdatedCity", "UpdatedAddress", "54321");
        String userId = "1";
        Warehouse existingWarehouse = new Warehouse(1, "Warehouse1", true, location, 1);
        Location updatedLocation = new Location(1, "UpdatedAddress", "UpdatedCity", "54321");

        when(warehouseRepository.findById(request.warehouseId())).thenReturn(Optional.of(existingWarehouse));
        when(locationRepository.findByPostalCode(request.postalCode())).thenReturn(Optional.of(updatedLocation));
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(existingWarehouse);

        Warehouse updatedWarehouse = warehouseService.updateWarehouse(request, userId);

        assertNotNull(updatedWarehouse);
        assertEquals("UpdatedWarehouse", updatedWarehouse.getName());
        assertFalse(updatedWarehouse.isRefrigerated());
        assertEquals("UpdatedCity", updatedWarehouse.getLocation().getCity());
    }

    @Test
    void shouldThrowWarehouseNotFoundExceptionWhenUserDoesNotOwnWarehouse() {
        UpdateWarehouseRequest request = new UpdateWarehouseRequest(1, "UpdatedWarehouse", false, "UpdatedCity", "UpdatedAddress", "54321");
        String userId = "2";
        Warehouse existingWarehouse = new Warehouse(1, "Warehouse1", true, location, 1);

        when(warehouseRepository.findById(request.warehouseId())).thenReturn(Optional.of(existingWarehouse));

        WarehouseNotFoundException exception = assertThrows(WarehouseNotFoundException.class, () -> {
            warehouseService.updateWarehouse(request, userId);
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void shouldDeleteWarehouse() {
        Integer warehouseId = 1;
        when(warehouseRepository.findById(warehouseId)).thenReturn(Optional.of(warehouse));

        warehouseService.deleteWarehouse(warehouseId);

        verify(warehouseRepository).delete(warehouse);
    }

    @Test
    void shouldReturnTrueIfWarehouseExistsForUser() {
        Integer warehouseId = 1;
        String userId = "1";
        when(warehouseRepository.findById(warehouseId)).thenReturn(Optional.of(warehouse));

        ServerResponse<Boolean> response = warehouseService.existsById(warehouseId, userId);

        assertTrue(response.getResponse());
    }

    @Test
    void shouldReturnFalseIfWarehouseDoesNotBelongToUser() {
        Integer warehouseId = 1;
        String userId = "2";
        when(warehouseRepository.findById(warehouseId)).thenReturn(Optional.of(warehouse));

        ServerResponse<Boolean> response = warehouseService.existsById(warehouseId, userId);

        assertFalse(response.getResponse());
    }

    @Test
    void shouldReturnAllWarehouses() {
        String loggedInUserId = "1";
        String teamAdminId = "";
        when(warehouseRepository.findAllByUserId(1)).thenReturn(List.of(warehouse));

        List<WarehouseResponse> warehouses = warehouseService.getAllWarehouses(loggedInUserId, teamAdminId);
        assertEquals(1, warehouses.size());
        assertEquals("Warehouse1", warehouses.get(0).name());
    }
}
