package org.peter.customer.dtos;

import lombok.Builder;

@Builder
public record AddressResponse(

        String street,

        String houseNumber,

        String city,

        String zipCode
) {
}
