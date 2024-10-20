package org.peter.customer.helpers;

import org.peter.customer.customer.Address;
import org.peter.customer.dtos.AddressRequest;
import org.peter.customer.dtos.AddressResponse;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {

    public Address toAddress(AddressRequest request) {
        return Address.builder()
                .city(request.city())
                .houseNumber(request.houseNumber())
                .street(request.street())
                .zipCode(request.zipCode())
                .build();
    }

    public AddressResponse toAddressResponse(Address address) {
        return AddressResponse
                .builder()
                .city(address.getCity())
                .houseNumber(address.getHouseNumber())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .build();
    }
}
