package org.inventory.product.product.inventory;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "inventory")
@EntityListeners(AuditingEntityListener.class)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @PositiveOrZero(message = "Cannot be negative")
    @Column(nullable = false)
    private Integer quantityAvailable;

    @PositiveOrZero(message = "Cannot be negative")
    @Column(nullable = false)
    private Integer minStockLevel;

    @PositiveOrZero(message = "Cannot be negative")
    @Column(nullable = false)
    private Integer maxStockLevel;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
