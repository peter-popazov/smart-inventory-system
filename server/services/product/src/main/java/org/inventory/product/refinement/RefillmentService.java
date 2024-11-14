package org.inventory.product.refinement;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.text.StrBuilder;
import org.inventory.product.clients.UserClient;
import org.inventory.product.clients.dto.AppUserResponse;
import org.inventory.product.exceptions.ProductNotFoundException;
import org.inventory.product.kafka.EmailRefinementProducer;
import org.inventory.product.product.Product;
import org.inventory.product.product.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefillmentService {

    private final ProductRepository productRepository;
    private final EmailRefinementProducer emailRefinementProducer;
    private final UserClient userClient;

    public void sendRefinementEmail(Integer productId, String userId) {

        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ProductNotFoundException("Product not found"));

        AppUserResponse appUserResponse = userClient.getUser(userId);
        StrBuilder sb = new StrBuilder(appUserResponse.firstName());
        sb.append(" ");
        sb.append(appUserResponse.lastName());

        LowStockProduct payload = LowStockProduct.builder()
                .userEmail(appUserResponse.email())
                .username(sb.toString())
                .productName(product.getName())
                .price(product.getPrice())
                .currentStock(product.getCurrentStock())
                .minStock(product.getMinStockLevel())
                .maxStock(product.getMaxStockLevel())
                .build();

        emailRefinementProducer.sendRefinementEmail(payload);
    }
}
