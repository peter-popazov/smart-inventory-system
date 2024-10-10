package org.inventory.product.product;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.category.Category;
import org.inventory.product.category.CategoryRepository;
import org.inventory.product.category.CreateProductRequest;
import org.inventory.product.exceptions.CategoryNotFoundException;
import org.inventory.product.exceptions.ProductNotFoundException;
import org.inventory.product.inventory.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts(String userId) {
        return productRepository.findAllByUserId(Integer.parseInt(userId))
                .stream()
                .map(ProductMapper::toProductResponse)
                .toList();
    }

    public ProductResponse getProductById(Integer id, String userId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        validateUser(userId, product.getUserId());

        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .availableQuantity(product.getInventory().getQuantityAvailable())
                .minStockLevel(product.getInventory().getMinStockLevel())
                .maxStockLevel(product.getInventory().getMaxStockLevel())
                .build();
    }

    public ServerResponse<Integer> createProduct(CreateProductRequest productRequest, String userId) {

        Category productCategory = categoryRepository.findByName(productRequest.name())
                .orElseGet(() -> Category.builder()
                        .name(productRequest.name())
                        .build());

        categoryRepository.save(productCategory);

        Inventory productInventory = Inventory.builder()
                .minStockLevel(productRequest.minStockLevel())
                .maxStockLevel(productRequest.minStockLevel())
                .quantityAvailable(productRequest.quantityAvailable())
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
                .inventory(productInventory)
                .build();

        Integer productId = productRepository.save(product).getProductId();

        return ServerResponse.<Integer>builder().response(productId).build();
    }

    public ServerResponse<Integer> updateProduct(Integer id, CreateProductRequest productRequest, String userId) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        validateUser(userId, existingProduct.getUserId());

        Category category = categoryRepository.findByName(productRequest.category())
                .orElseThrow(() -> new CategoryNotFoundException("Category " + productRequest.category() + " not found"));

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
        existingProduct.getInventory().setMinStockLevel(productRequest.minStockLevel());
        existingProduct.getInventory().setMaxStockLevel(productRequest.maxStockLevel());
        existingProduct.getInventory().setQuantityAvailable(productRequest.quantityAvailable());

        return ServerResponse.<Integer>builder().response(productRepository.save(existingProduct).getProductId()).build();
    }

    public String deleteProductById(Integer id, String userId) {
        Product productOptional = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        validateUser(userId, productOptional.getUserId());

        productRepository.deleteById(id);
        return "Deleted product with ID " + id;
    }

    private void validateUser(String userId, Integer productUserId) {
        if (productUserId != Integer.parseInt(userId)) {
            throw new RuntimeException("User id mismatch");
        }
    }
}
