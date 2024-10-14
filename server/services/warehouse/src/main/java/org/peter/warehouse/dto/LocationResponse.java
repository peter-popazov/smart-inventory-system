package org.peter.warehouse.dto;

import lombok.Builder;

@Builder
public record LocationResponse(

        String address,
        String city,
        String postalCode

) {
}
