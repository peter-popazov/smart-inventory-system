package org.inventory.product;

import org.inventory.product.exceptions.InventoryNotFoundException;
import org.inventory.product.exceptions.ProductNotFoundException;
import org.inventory.product.inventory.Inventory;
import org.inventory.product.inventory.InventoryRepository;
import org.inventory.product.movements.StockMovementRepository;
import org.inventory.product.movements.StockMovementType;
import org.inventory.product.movements.StockMovements;
import org.inventory.product.movements.StockMovementsService;
import org.inventory.product.movements.dto.StockLevelsResponse;
import org.inventory.product.movements.dto.StockMovementsRequest;
import org.inventory.product.movements.dto.StockMovementsResponse;
import org.inventory.product.movements.helpers.StockMapper;
import org.inventory.product.product.Product;
import org.inventory.product.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StockMovementTest {
    @Mock
    private StockMovementRepository stockMovementRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private StockMapper stockMapper;

    @InjectMocks
    private StockMovementsService stockMovementsService;

    private Product testProduct;
    private Inventory testInventory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testProduct = Product.builder()
                .productId(1)
                .userId(1)
                .name("Test Product")
                .build();

        testInventory = Inventory.builder()
                .inventoryId(1)
                .warehouseId(1)
                .product(testProduct)
                .stockAvailable(100)
                .build();
    }

    @Test
    void getMovementsForProduct_shouldReturnSortedMovements() {
        List<StockMovements> movements = List.of(
                StockMovements.builder().createdAt(LocalDateTime.now().minusDays(1)).build(),
                StockMovements.builder().createdAt(LocalDateTime.now()).build()
        );
        when(stockMovementRepository.findByProductId(1)).thenReturn(movements);
        when(stockMapper.toStockMovementsResponse(any())).thenReturn(
                new StockMovementsResponse(1, testProduct.getName(), testProduct.getProductId(),
                        10, 20, StockMovementType.ADJUSTMENT, "now"));

        List<StockMovementsResponse> result = stockMovementsService.getMovementsForProduct(1);

        verify(stockMovementRepository, times(1)).findByProductId(1);
        assert result.size() == 2;
    }

    @Test
    void addMovementsForProduct_shouldSaveMovementAndUpdateInventory() {
        StockMovementsRequest request = new StockMovementsRequest(1, 1, 10, StockMovementType.PURCHASE);
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(inventoryRepository.findByWarehouseIdAndProduct(1, testProduct)).thenReturn(Optional.of(testInventory));

        stockMovementsService.addMovementsForProduct(request);

        verify(inventoryRepository, times(1)).save(any(Inventory.class));
        verify(stockMovementRepository, times(1)).save(any(StockMovements.class));
    }

    @Test
    void addMovementsForProduct_shouldThrowProductNotFoundException() {
        StockMovementsRequest request = new StockMovementsRequest(testProduct.getProductId(), 1, 10, StockMovementType.PURCHASE);
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> stockMovementsService.addMovementsForProduct(request));
    }

    @Test
    void addMovementsForProduct_shouldThrowInventoryNotFoundException() {
        StockMovementsRequest request = new StockMovementsRequest(testProduct.getProductId(), 1, 10, StockMovementType.PURCHASE);
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(inventoryRepository.findByWarehouseIdAndProduct(1, testProduct)).thenReturn(Optional.empty());

        assertThrows(InventoryNotFoundException.class, () -> stockMovementsService.addMovementsForProduct(request));
    }

    @Test
    void addMovementsForProduct_shouldThrowIllegalArgumentException_onSaleWithInsufficientStock() {
        StockMovementsRequest request = new StockMovementsRequest(1, 1, 200, StockMovementType.SALE);
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(inventoryRepository.findByWarehouseIdAndProduct(1, testProduct)).thenReturn(Optional.of(testInventory));

        assertThrows(IllegalArgumentException.class, () -> stockMovementsService.addMovementsForProduct(request));
    }

    @Test
    void getMovements_shouldReturnUserMovements() {
        List<StockMovements> movements = List.of(
                StockMovements.builder().createdAt(LocalDateTime.now()).build()
        );
        when(stockMovementRepository.findByUserId(1)).thenReturn(movements);
        when(stockMapper.toStockMovementsResponse(any())).thenReturn(
                new StockMovementsResponse(1, testProduct.getName(), testProduct.getProductId(),
                        10, 20, StockMovementType.ADJUSTMENT, "now"));

        List<StockMovementsResponse> result = stockMovementsService.getMovements("1", "1");

        verify(stockMovementRepository, times(1)).findByUserId(1);
        assert result.size() == 1;
    }

    @Test
    void findStockStatistics_shouldReturnAggregatedStockLevels() {
        StockMovements movement = StockMovements.builder()
                .createdAt(LocalDateTime.now())
                .productQuantityUpdated(50)
                .build();
        testProduct.setStockMovements(List.of(movement));
        when(productRepository.findAllByUserId(1)).thenReturn(List.of(testProduct));

        List<StockLevelsResponse> result = stockMovementsService.findStockStatistics("1", "");

        assert result.size() == 1;
        assert result.get(0).level() == 50;
    }
}
