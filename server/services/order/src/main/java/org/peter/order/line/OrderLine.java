package org.peter.order.line;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.peter.order.helpers.BaseEntity;
import org.peter.order.order.Order;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_line")
public class OrderLine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderLineId;

    @Column(nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal unitPrice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
