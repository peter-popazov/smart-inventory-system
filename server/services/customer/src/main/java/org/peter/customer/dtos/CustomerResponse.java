package org.peter.customer.dtos;


import lombok.Builder;

@Builder
public record CustomerResponse(

        String id,

        String firstName,

        String lastName,

        String email,

        String phone,

        AddressResponse address
) {
}
