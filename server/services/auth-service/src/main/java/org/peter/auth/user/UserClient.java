package org.peter.auth.user;

import org.peter.auth.helpers.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "app-user-service", url = "http://localhost:8090/api/v1/user")
public interface UserClient {

    @GetMapping("/{email}")
    UserResponse getUser(@PathVariable("email") String email, @RequestHeader("loggedInUserId") String loggedInUserId);
}
