package org.peter.warehouse.location;


import jakarta.persistence.*;
import lombok.*;
import org.peter.warehouse.helpers.BaseEntity;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "location")
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationId;

    private String address;

    private String city;

    private String postalCode;
}


