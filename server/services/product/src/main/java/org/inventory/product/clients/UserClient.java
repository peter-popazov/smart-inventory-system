package org.inventory.product.clients;

import org.inventory.product.clients.dto.AppUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "app-user-service", url = "http://localhost:8090/api/v1")
public interface UserClient {

    @GetMapping("/user/team")
    List<Integer> getUsersByTeamId(@RequestHeader("loggedInUserId") String loggedInUserId);

    @GetMapping("/teams/{userId}")
    Integer getAdminForUserTeam(@PathVariable Integer userId);

    @GetMapping("/user")
    AppUserResponse getUser(@RequestHeader("loggedInUserId") String loggedInUserId);
}