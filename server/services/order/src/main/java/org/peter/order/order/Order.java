package org.peter.order.order;

import jakarta.persistence.*;
import lombok.*;
import org.peter.order.helpers.BaseEntity;
import org.peter.order.helpers.PaymentMethod;
import org.peter.order.line.OrderLine;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_order")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private String reference;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;

    @Column(nullable = false)
    private String customerId;
}
