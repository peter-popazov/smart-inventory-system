package org.peter.customer.helpers;

import lombok.RequiredArgsConstructor;
import org.peter.customer.customer.Customer;
import org.peter.customer.dtos.UpdateCustomerRequest;
import org.peter.customer.dtos.CustomerResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerMapper {

    private final AddressMapper addressMapper;

    public Customer toCustomer(UpdateCustomerRequest request) {
        return Customer.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .address(addressMapper.toAddress(request.address()))
                .build();
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress() == null ? null : addressMapper.toAddressResponse(customer.getAddress()))
                .build();
    }
}
