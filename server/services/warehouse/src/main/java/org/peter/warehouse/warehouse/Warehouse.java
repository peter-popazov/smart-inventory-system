package org.peter.warehouse.warehouse;

import jakarta.persistence.*;
import lombok.*;
import org.peter.warehouse.helpers.BaseEntity;
import org.peter.warehouse.location.Location;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "warehouse")
public class Warehouse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer warehouseId;

    private String name;

    private boolean isRefrigerated;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
