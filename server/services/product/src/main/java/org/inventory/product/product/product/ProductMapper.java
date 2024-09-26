package org.inventory.product.product.product;

public class ProductMapper {

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .productCode(product.getProductCode())
                .availableQuantity(product.getInventory().getQuantityAvailable())
                .maxStockLevel(product.getInventory().getMaxStockLevel())
                .minStockLevel(product.getInventory().getMinStockLevel())
                .categoryName(product.getCategory().getName())
                .photoUrl("URL")
                .build();
    }
}
