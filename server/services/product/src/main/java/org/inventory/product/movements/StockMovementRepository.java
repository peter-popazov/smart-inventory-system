package org.inventory.product.movements;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovements, Integer> {

    @Query("SELECT sm FROM StockMovements sm WHERE sm.product.productId = :productId ORDER BY sm.createdAt DESC")
    List<StockMovements> findByProductId(Integer productId);

    @Query("SELECT sm FROM StockMovements sm WHERE sm.product.userId = :userId ORDER BY sm.createdAt DESC")
    List<StockMovements> findByUserId(Integer userId);
}
