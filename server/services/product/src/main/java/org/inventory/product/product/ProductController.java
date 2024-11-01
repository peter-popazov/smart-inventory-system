package org.inventory.product.product;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(@RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(productService.getAllProducts(loggedInUserId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id,
                                                          @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(productService.getProductById(id, loggedInUserId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ServerResponse<Integer>> createProduct(@RequestBody CreateProductRequest product,
                                                                 @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(productService.createProduct(product, loggedInUserId), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<ServerResponse<Integer>> updateProduct(@RequestBody UpdateProductRequest productRequest,
                                                                 @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(productService.updateProduct(productRequest, loggedInUserId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id, @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(productService.deleteProductById(id, loggedInUserId), HttpStatus.OK);
    }

    @GetMapping("/warehouses")
    public ResponseEntity<List<Integer>> getWarehousesByUserId(@RequestParam("userId") String userId) {
        return new ResponseEntity<>(productService.getWarehousesId(userId), HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<PurchaseProductsResponse>> purchaseProducts(@RequestBody List<PurchaseProductsRequest> purchaseProductsRequests) {
        return new ResponseEntity<>(productService.purchaseProducts(purchaseProductsRequests), HttpStatus.OK);
    }
}
