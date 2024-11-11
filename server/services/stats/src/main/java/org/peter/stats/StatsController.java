package org.peter.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stats")
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public ResponseEntity<DashboardStats> getDashboardStats(@RequestHeader("loggedInUserId") String loggedInUserId,
                                                            @RequestHeader("teamAdminId") String teamAdminId) {
        return new ResponseEntity<>(statsService.getDashboardStats(loggedInUserId, teamAdminId), HttpStatus.OK);
    }
}
