package org.inventory.product.inventory;

import org.inventory.product.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Optional<Inventory> findByWarehouseIdAndProduct(Integer id, Product product);

}
