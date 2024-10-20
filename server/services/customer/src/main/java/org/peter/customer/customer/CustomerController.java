package org.peter.customer.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.peter.customer.dtos.UpdateCustomerRequest;
import org.peter.customer.dtos.CustomerResponse;
import org.peter.customer.helpers.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PutMapping
    public ResponseEntity<ServerResponse<Boolean>> updateCustomer(@RequestBody @Valid UpdateCustomerRequest request,
                                                                  @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(customerService.updateCustomer(request, loggedInUserId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable String id,
                                                             @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(customerService.findCustomerById(id, loggedInUserId), HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<ServerResponse<Boolean>> deleteCustomerById(@PathVariable String customerId,
                                                                      @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(customerService.deleteCustomerById(customerId, loggedInUserId), HttpStatus.OK);
    }
}
