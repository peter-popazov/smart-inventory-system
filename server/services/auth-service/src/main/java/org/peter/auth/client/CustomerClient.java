package org.peter.auth.client;

import org.peter.auth.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "customer-service", url = "http://localhost:8030/api/v1/customers")
public interface CustomerClient {

    @GetMapping("/auth/{email}")
    UserResponse getUser(@PathVariable("email") String email, @RequestHeader("loggedInUserId") String loggedInUserId);
}