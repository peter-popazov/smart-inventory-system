package org.peter.customer.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AddressRequest (

        @NotNull(message = "Street is required")
        String street,

        @NotNull(message = "House number is required")
        String houseNumber,

        @NotNull(message = "City is required")
        String city,

        @NotNull(message = "ZipCode is required")
        String zipCode
) {
}
