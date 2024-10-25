package org.peter.order.client;

import org.peter.order.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@FeignClient(name = "customer-service", url = "${application.config.customer-service}")
public interface CustomerClient {

    @GetMapping("/{id}")
    Optional<CustomerResponse> findCustomerById(@PathVariable("id") String id,
                                                @RequestHeader("loggedInUserId") String loggedInUserId);
}
