package org.peter.customer.customer;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.peter.customer.dtos.UpdateCustomerRequest;
import org.peter.customer.dtos.CustomerResponse;
import org.peter.customer.exceptions.CustomerNotFoundException;
import org.peter.customer.exceptions.InsufficientPrivilegesException;
import org.peter.customer.helpers.AddressMapper;
import org.peter.customer.helpers.CustomerMapper;
import org.peter.customer.helpers.ServerResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;

    public void registerCustomer(Integer registeredCustomerId, String email) {
        customerRepository.save(Customer.builder()
                .registeredUserId(registeredCustomerId)
                .email(email)
                .build());
    }

    public ServerResponse<Boolean> updateCustomer(UpdateCustomerRequest request, String loggedInUserId) {

        Customer customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + request.id()));

        validateUser(customer.getRegisteredUserId(), loggedInUserId);

        mergeCustomer(customer, request);
        customerRepository.save(customer);
        return ServerResponse.<Boolean>builder().response(true).build();
    }

    public CustomerResponse findCustomerById(String id, String loggedInUserid) {
        validateUser(id, loggedInUserid);
        return customerMapper.toCustomerResponse(customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id)));
    }

    public ServerResponse<Boolean> deleteCustomerById(String customerId, String loggedInUserId) {
        validateUser(customerId, loggedInUserId);
        customerRepository.deleteById(customerId);
        return ServerResponse.<Boolean>builder().response(true).build();
    }

    private void validateUser(Integer registeredUserId, String loggedInUserId) {
        if (registeredUserId != Integer.parseInt(loggedInUserId)) {
            throw new InsufficientPrivilegesException("Logged in user cannot perform actions on customer with ID: " + registeredUserId);
        }
    }

    private void validateUser(String userId, String loggedInUserId) {
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + userId));
        validateUser(customer.getRegisteredUserId(), loggedInUserId);
    }

    private void mergeCustomer(Customer customer, UpdateCustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())) {
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
            customer.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (StringUtils.isNotBlank(request.phone())) {
            customer.setPhone(request.phone());
        }
        if (request.address() != null) {
            customer.setAddress(addressMapper.toAddress(request.address()));
        }
    }
}
