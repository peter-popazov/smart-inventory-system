package org.inventory.product.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.inventory.product.category.Category;
import org.inventory.product.inventory.Inventory;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false)
    private String productCode;

    @Column(nullable = false)
    private String barcode;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @PositiveOrZero(message = "Cannot be negative")
    private Double weight;

    @PositiveOrZero(message = "Cannot be negative")
    private Double height;

    @PositiveOrZero(message = "Cannot be negative")
    private Double depth;

    @PositiveOrZero(message = "Cannot be negative")
    private Double width;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Inventory> inventory;

    @PositiveOrZero(message = "Cannot be negative")
    @Column(nullable = false)
    private Integer minStockLevel;

    @PositiveOrZero(message = "Cannot be negative")
    @Column(nullable = false)
    private Integer maxStockLevel;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreatedDate
    private LocalDateTime updatedAt;

    @PositiveOrZero(message = "Cannot be negative")
    @Column(nullable = false)
    private Integer userId;
}
