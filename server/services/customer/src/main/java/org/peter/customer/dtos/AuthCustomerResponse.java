package org.peter.customer.dtos;


import lombok.Builder;

@Builder
public record AuthCustomerResponse(

        String firstName,
        String lastName,
        String email,
        String role
) {
}
