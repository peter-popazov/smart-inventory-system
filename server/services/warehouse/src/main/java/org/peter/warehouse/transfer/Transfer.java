package org.peter.warehouse.transfer;

import jakarta.persistence.*;
import lombok.*;
import org.peter.warehouse.helpers.BaseEntity;
import org.peter.warehouse.warehouse.Warehouse;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transfer")
public class Transfer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transferId;

    private Integer quantity;

    private LocalDateTime sendDate;

    private LocalDateTime receiveDate;

    private Integer productId;

    @Enumerated(EnumType.STRING)
    private TransferStatus transferStatus;

    @ManyToOne
    @JoinColumn(name = "warehouse_id_to")
    private Warehouse warehouseTo;

    @ManyToOne
    @JoinColumn(name = "warehouse_id_from")
    private Warehouse warehouseFrom;
}
