package org.inventory.appuser.team;

import lombok.RequiredArgsConstructor;
import org.inventory.appuser.team.dto.AddTeamMemberRequest;
import org.inventory.appuser.team.dto.CreateTeamRequest;
import org.inventory.appuser.team.dto.RemoveTeamMemberRequest;
import org.inventory.appuser.team.dto.TeamResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getTeams(@RequestHeader("loggedInUserId") String loggedInUserId,
                                                       @RequestHeader("teamAdminId") String teamAdminId) {
        return ResponseEntity.ok(teamService.getTeams(loggedInUserId, teamAdminId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<TeamResponse>> getTeamForUser(@RequestHeader("loggedInUserId") String loggedInUserId,
                                                             @RequestHeader("teamAdminId") String teamAdminId) {
        return ResponseEntity.ok(teamService.getTeams(loggedInUserId, teamAdminId));
    }

    @PostMapping
    public ResponseEntity<String> createTeam(@RequestBody CreateTeamRequest request,
                                             @RequestHeader("loggedInUserId") String loggedInUserId) {
        teamService.createTeam(request, loggedInUserId);
        return ResponseEntity.ok("Team created successfully");
    }

    @PostMapping("/invite")
    public ResponseEntity<?> addTeamMembers(@RequestBody AddTeamMemberRequest request,
                                            @RequestHeader("loggedInUserId") String loggedInUserId) {
        teamService.addTeamMembers(request, loggedInUserId);
        return ResponseEntity.ok("Team added successfully");
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeTeamMembers(@RequestBody RemoveTeamMemberRequest request,
                                               @RequestHeader("loggedInUserId") String loggedInUserId) {
        teamService.removeTeamMembers(request, loggedInUserId);
        return ResponseEntity.ok("Team member [%s] removed successfully".formatted(request.userEmail()));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer teamId,
                                        @RequestHeader("loggedInUserId") String loggedInUserId) {
        teamService.deleteTeam(teamId, loggedInUserId);
        return ResponseEntity.ok("Team deleted successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> adminForUserTeam(@PathVariable Integer userId) {
        return ResponseEntity.ok(teamService.getAdminIdForUserTeam(userId));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getTeamSize(@RequestHeader("userId") String userId) {
        return new ResponseEntity<>(teamService.getTeamsSize(userId), HttpStatus.OK);
    }
}
