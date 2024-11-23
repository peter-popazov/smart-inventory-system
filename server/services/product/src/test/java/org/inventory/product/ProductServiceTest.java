package org.inventory.product;

import org.inventory.product.ServerResponse;
import org.inventory.product.category.Category;
import org.inventory.product.category.CategoryRepository;
import org.inventory.product.dto.*;
import org.inventory.product.inventory.Inventory;
import org.inventory.product.inventory.InventoryRepository;
import org.inventory.product.inventory.InventoryService;
import org.inventory.product.movements.StockMovementsService;
import org.inventory.product.product.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private InventoryRepository inventoryRepository;

    @MockBean
    private WarehouseClient warehouseClient;

    @Autowired
    private ProductService productService;

    @Test
    public void testGetProductById() {
        Integer productId = 1;
        String userId = "1";

        // Mocking the product retrieval
        Product product = new Product();
        product.setProductId(productId);
        product.setUserId(1);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(10));
        product.setCategory(new Category(1, "Category", 1, LocalDateTime.now(), null));

        Inventory inventory = new Inventory(1, 10, product, 2, LocalDateTime.now());

        product.setInventory(List.of(inventory));

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        WarehouseResponse warehouseResponse = new WarehouseResponse(1, "Test Warehouse", true, null);
        when(warehouseClient.getWarehouseById(anyInt())).thenReturn(warehouseResponse);

        ProductResponse result = productService.getProductById(productId, userId);

        assertNotNull(result);
        assertEquals("Test Product", result.name());
    }

    @Test
    public void testCreateProduct() {
        CreateProductRequest request = new CreateProductRequest("34394023", "89349032", "Test Product", "Test Product", BigDecimal.valueOf(1), 33.22, 10.00, 100.0, 20.0, "test category", 5, 15, 5, 1);
        String userId = "1";
        String teamAdminId = "1";

        // Mocking the repository calls
        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Category");

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

        Product product = new Product();
        product.setProductId(1);
        product.setName(request.productName());
        product.setCategory(category);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ServerResponse<Integer> result = productService.createProduct(request, userId, teamAdminId);

        assertNotNull(result);
        assertEquals(1, result.getResponse());
    }

    @Test
    public void testDeleteProductById() {
        Integer productId = 1;
        String userId = "1";

        Product product = new Product();
        product.setProductId(productId);
        product.setUserId(1);

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        doNothing().when(productRepository).deleteById(productId);

        String result = productService.deleteProductById(productId, userId);

        assertEquals("Deleted product with ID " + productId, result);
    }
}
