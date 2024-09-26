package org.inventory.product.product.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.inventory.product.product.ServerResponse;
import org.inventory.product.product.category.Category;
import org.inventory.product.product.category.CategoryRepository;
import org.inventory.product.product.category.CreateProductRequest;
import org.inventory.product.product.inventory.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toProductResponse)
                .toList();
    }

    public ProductResponse getProductById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .availableQuantity(product.getInventory().getQuantityAvailable())
                .minStockLevel(product.getInventory().getMinStockLevel())
                .maxStockLevel(product.getInventory().getMaxStockLevel())
                .build();
    }

    public ServerResponse<Integer> createProduct(CreateProductRequest productRequest) {

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
                .width(productRequest.width())
                .height(productRequest.height())
                .depth(productRequest.depth())
                .weight(productRequest.weight())
                .inventory(productInventory)
                .build();

        Integer productId = productRepository.save(product).getProductId();

        return ServerResponse.<Integer>builder().response(productId).build();
    }

    public ServerResponse<Integer> updateProduct(Integer id, CreateProductRequest productRequest) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));

        Category category = categoryRepository.findByName(productRequest.category())
                .orElseThrow(() -> new IllegalArgumentException("Category " + productRequest.category() + " not found"));

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

    public String deleteProductById(Integer id) {
        Product productOptional = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));

        productRepository.deleteById(id);
        return "Deleted product with ID " + id;
    }
}
