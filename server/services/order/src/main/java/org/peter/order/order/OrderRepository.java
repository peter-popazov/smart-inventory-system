package org.peter.order.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT SUM(ol.quantity * ol.unitPrice) FROM OrderLine ol WHERE ol.productOwner = :ownerId")
    BigDecimal calculateTotalEarningsByOwner(@Param("ownerId") Integer ownerId);
}
