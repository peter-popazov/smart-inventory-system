package org.peter.customer.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UpdateCustomerRequest(

        String id,

        @NotNull(message = "First name is required")
        String firstName,

        @NotNull(message = "Last name is required")
        String lastName,

        @NotNull(message = "Email is required")
        @Email(message = "Email is not valid")
        String email,

        @NotNull(message = "Phone is required")
        String phone,

        @NotNull(message = "Address is required")
        AddressRequest address
) {
}
