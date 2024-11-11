package org.inventory.product.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByUserId(Integer userId);

    @Query("SELECT p.name FROM product p WHERE p.productId = :productId")
    String findProductNameById(@Param("productId") Integer productId);
}