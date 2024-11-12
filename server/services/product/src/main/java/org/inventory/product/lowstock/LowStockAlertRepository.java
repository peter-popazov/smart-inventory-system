package org.inventory.product.lowstock;

import org.inventory.product.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LowStockAlertRepository extends JpaRepository<LowStockAlert, Long> {

    @Query("SELECT lst FROM LowStockAlert lst WHERE lst.product.userId = :userId AND lst.active = true")
    List<LowStockAlert> findAllByUserId(@Param("userId") Integer userId);

    Optional<LowStockAlert> findByProductAndActive(Product product, boolean active);

    Optional<LowStockAlert> findByAlertIdAndActive(Integer alertId, boolean active);
}
