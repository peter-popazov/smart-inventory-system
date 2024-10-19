package org.inventory.product.product;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.category.Category;
import org.inventory.product.category.CategoryRepository;
import org.inventory.product.dto.*;
import org.inventory.product.exceptions.CategoryNotFoundException;
import org.inventory.product.exceptions.ProductNotFoundException;
import org.inventory.product.inventory.Inventory;
import org.inventory.product.inventory.InventoryMapper;
import org.inventory.product.inventory.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.inventory.product.helpers.UserHelpers.validateUser;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final WarehouseClient warehouseClient;
    private final ProductMapper productMapper;

    public List<ProductResponse> getAllProducts(String userId) {
        return productRepository.findAllByUserId(Integer.parseInt(userId))
                .stream()
                .map(product -> productMapper.toProductResponse(product, userId))
                .toList();
    }

    public ProductResponse getProductById(Integer id, String userId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        validateUser(userId, product.getUserId());

        List<InventoryResponse> inventories = product.getInventory().stream()
                .map(inventory -> {
                    WarehouseResponse warehouse = warehouseClient.getWarehouseById(inventory.getWarehouseId(), userId);
                    return InventoryMapper.toInventoryResponse(inventory, warehouse);
                })
                .toList();

        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .inventories(inventories)
                .categoryName(product.getCategory().getName())
                .photoUrl("URL")
                .build();
    }

    public ServerResponse<Integer> createProduct(CreateProductRequest productRequest, String userId) {

        Category productCategory = categoryRepository.findByName(productRequest.name())
                .orElseGet(() -> {
                    Category newCategory = Category.builder()
                            .name(productRequest.name())
                            .build();
                    return categoryRepository.save(newCategory);
                });

        Inventory productInventory = Inventory.builder()
                .stockAvailable(productRequest.quantityAvailable())
                .warehouseId(productRequest.warehouseId())
                .build();

        Product product = Product.builder()
                .productCode(productRequest.productCode())
                .barcode(productRequest.barcode())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .category(productCategory)
                .userId(Integer.parseInt(userId))
                .width(productRequest.width())
                .height(productRequest.height())
                .depth(productRequest.depth())
                .weight(productRequest.weight())
                .minStockLevel(productRequest.minStockLevel())
                .maxStockLevel(productRequest.maxStockLevel())
                .inventory(List.of(productInventory))
                .build();

        productInventory.setProduct(product);

        Integer productId = productRepository.save(product).getProductId();

        return ServerResponse.<Integer>builder().response(productId).build();
    }

    public ServerResponse<Integer> updateProduct(Integer productId,
                                                 UpdateProductRequest productRequest, String userId) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));

        validateUser(userId, existingProduct.getUserId());

        Category category = categoryRepository.findByName(productRequest.category())
                .orElseThrow(() -> new CategoryNotFoundException("Category " + productRequest.category() + " not found"));

        Inventory inventory = inventoryRepository.findById(productRequest.inventoryId())
                .orElseThrow(() -> new RuntimeException("Inventory with ID " + productRequest.inventoryId() + " not found"));

        existingProduct.setProductCode(productRequest.productCode());
        existingProduct.setBarcode(productRequest.barcode());
        existingProduct.setName(productRequest.name());
        existingProduct.setDescription(productRequest.description());
        existingProduct.setPrice(productRequest.price());
        existingProduct.setWeight(productRequest.weight());
        existingProduct.setHeight(productRequest.height());
        existingProduct.setDepth(productRequest.depth());
        existingProduct.setWidth(productRequest.width());
        existingProduct.setCategory(category);
        existingProduct.setMaxStockLevel(productRequest.maxStockLevel());
        existingProduct.setMinStockLevel(productRequest.minStockLevel());

        inventory.setStockAvailable(productRequest.quantityAvailable());
        inventory.setWarehouseId(productRequest.warehouseId());

        inventoryRepository.save(inventory);

        return ServerResponse.<Integer>builder().response(productRepository.save(existingProduct).getProductId()).build();
    }

    public String deleteProductById(Integer id, String userId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        validateUser(userId, product.getUserId());

        productRepository.deleteById(id);
        return "Deleted product with ID " + id;
    }

    public List<Integer> getWarehousesId(String userId) {

        List<Product> products = productRepository.findAllByUserId(Integer.parseInt(userId));
        return products.stream()
                .flatMap(product -> product.getInventory().stream())
                .map(Inventory::getWarehouseId)
                .distinct()
                .toList();
    }

}
