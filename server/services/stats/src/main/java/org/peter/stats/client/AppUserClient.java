package org.peter.stats.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "app-user-server", url = "${application.app-user-server.url}")
public interface AppUserClient {

    @GetMapping("/size")
    Integer getTeamsStats(@RequestHeader("userId") String userId);
}
