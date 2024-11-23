package org.peter.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.peter.customer.customer.Customer;
import org.peter.customer.customer.CustomerRepository;
import org.peter.customer.customer.CustomerService;
import org.peter.customer.dtos.CustomerResponse;
import org.peter.customer.dtos.UpdateCustomerRequest;
import org.peter.customer.exceptions.CustomerNotFoundException;
import org.peter.customer.exceptions.InsufficientPrivilegesException;
import org.peter.customer.helpers.AddressMapper;
import org.peter.customer.helpers.CustomerMapper;
import org.peter.customer.helpers.ServerResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testCustomer = Customer.builder()
                .id("1")
                .registeredUserId(1)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .phone("1234567890")
                .build();
    }

    @Test
    void registerCustomer_shouldSaveCustomer() {
        Integer registeredUserId = 1;
        String email = "test@example.com";

        customerService.registerCustomer(registeredUserId, email);

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomer_shouldUpdateAndSaveCustomer() {
        UpdateCustomerRequest request = new UpdateCustomerRequest("1", "Jane", "Doe", "jane.doe@example.com", "9876543210", null);
        when(customerRepository.findById("1")).thenReturn(Optional.of(testCustomer));

        ServerResponse<Boolean> response = customerService.updateCustomer(request, "1");

        verify(customerRepository, times(1)).save(any(Customer.class));
        assert response.getResponse();
    }

    @Test
    void updateCustomer_shouldThrowCustomerNotFoundException_whenCustomerDoesNotExist() {
        UpdateCustomerRequest request = new UpdateCustomerRequest("2", "Jane", "Doe", "jane.doe@example.com", "9876543210", null);
        when(customerRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(request, "1"));
    }

    @Test
    void findCustomerById_shouldReturnCustomerResponse() {
        when(customerRepository.findById("1")).thenReturn(Optional.of(testCustomer));
        CustomerResponse response = new CustomerResponse("1", "John", "Doe", "test@example.com", "1234567890", null);
        when(customerMapper.toCustomerResponse(testCustomer)).thenReturn(response);

        CustomerResponse result = customerService.findCustomerById("1", "1");

        assert result.equals(response);
        verify(customerRepository, times(2)).findById("1");
    }

    @Test
    void findCustomerById_shouldThrowCustomerNotFoundException_whenCustomerDoesNotExist() {
        when(customerRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.findCustomerById("2", "1"));
    }

    @Test
    void deleteCustomerById_shouldDeleteCustomer() {
        when(customerRepository.findById("1")).thenReturn(Optional.of(testCustomer));

        ServerResponse<Boolean> response = customerService.deleteCustomerById("1", "1");

        verify(customerRepository, times(1)).deleteById("1");
        assert response.getResponse(); // Ensure the response is true
    }

    @Test
    void deleteCustomerById_shouldThrowCustomerNotFoundException_whenCustomerDoesNotExist() {
        when(customerRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomerById("2", "1"));
    }

    @Test
    void validateUser_shouldThrowInsufficientPrivilegesException_whenUserIdsMismatch() {
        when(customerRepository.findById("1")).thenReturn(Optional.of(testCustomer));

        assertThrows(InsufficientPrivilegesException.class, () -> customerService.findCustomerById("1", "2"));
    }

    @Test
    void validateUser_shouldNotThrowException_whenUserIdsMatch() {
        when(customerRepository.findById("1")).thenReturn(Optional.of(testCustomer));
        customerService.findCustomerById("1", "1");
        verify(customerRepository, times(2)).findById("1");
    }
}
