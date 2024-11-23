package org.inventory.product;

import org.inventory.product.inventory.Inventory;
import org.inventory.product.lowstock.LowStockAlert;
import org.inventory.product.lowstock.LowStockAlertRepository;
import org.inventory.product.lowstock.LowStockAlertService;
import org.inventory.product.lowstock.dto.LowStockAlertResponse;
import org.inventory.product.lowstock.helpers.LowStockAlertMapper;
import org.inventory.product.movements.StockMovementsService;
import org.inventory.product.movements.dto.StockMovementsRequest;
import org.inventory.product.product.Product;
import org.inventory.product.refinement.RefillmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LowStockAlertServiceTest {

    @Mock private LowStockAlertRepository lowStockAlertRepository;
    @Mock private LowStockAlertMapper lowStockAlertMapper;
    @Mock private RefillmentService refillmentService;
    @Mock private StockMovementsService stockMovementsService;

    @InjectMocks
    private LowStockAlertService lowStockAlertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAlertsForUserOrTeamAdmin() {
        // Given
        String userId = "1";
        String teamAdminId = "";
        LowStockAlert alert = mock(LowStockAlert.class);
        LowStockAlertResponse response = mock(LowStockAlertResponse.class);
        when(lowStockAlertRepository.findAllByUserId(Integer.parseInt(userId))).thenReturn(List.of(alert));
        when(lowStockAlertMapper.toLowStockAlertResponse(alert)).thenReturn(response);

        List<LowStockAlertResponse> alerts = lowStockAlertService.getAlerts(userId, teamAdminId);

        assertEquals(1, alerts.size());
        assertSame(response, alerts.get(0));
    }

    @Test
    void shouldCreateNewAlertWhenNoExistingAlert() {
        Product product = mock(Product.class);
        product.setProductId(1);
        product.setUserId(1);

        when(lowStockAlertRepository.findByProductAndActive(product, true)).thenReturn(Optional.empty());
        lowStockAlertService.addOrMergeAlert(product);

        verify(lowStockAlertRepository).save(any(LowStockAlert.class));
        verify(refillmentService).sendRefinementEmail(product.getProductId(), String.valueOf(product.getUserId()));
    }

    @Test
    void shouldUpdateExistingAlertWhenAlertExists() {
        Product product = mock(Product.class);
        LowStockAlert existingAlert = mock(LowStockAlert.class);
        when(lowStockAlertRepository.findByProductAndActive(product, true)).thenReturn(Optional.of(existingAlert));

        lowStockAlertService.addOrMergeAlert(product);

        verify(existingAlert).setUpdatedAt(any(LocalDateTime.class));
        verifyNoInteractions(refillmentService); // No email should be sent
    }

    @Test
    void shouldProcessAlertWhenAlertIsValid() {
        Integer alertId = 1;
        Product product = mock(Product.class);
        LowStockAlert alert = mock(LowStockAlert.class);
        when(lowStockAlertRepository.findByAlertIdAndActive(alertId, true)).thenReturn(Optional.of(alert));
        when(alert.getProduct()).thenReturn(product);
        when(product.getMaxStockLevel()).thenReturn(100);
        when(product.getCurrentStock()).thenReturn(50);
        when(product.getInventory()).thenReturn(List.of(mock(Inventory.class)));

        lowStockAlertService.processAlert(alertId);

        verify(stockMovementsService).addMovementsForProduct(any(StockMovementsRequest.class));
        verify(alert).setActive(false);
        verify(lowStockAlertRepository).save(alert);
    }

    @Test
    void shouldThrowExceptionWhenAlertNotFound() {
        Integer alertId = 1;
        when(lowStockAlertRepository.findByAlertIdAndActive(alertId, true)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> lowStockAlertService.processAlert(alertId));
        assertEquals("Alert not found", exception.getMessage());
    }
}
