package org.inventory.appuser.team;

import lombok.RequiredArgsConstructor;
import org.inventory.appuser.team.requests.AddTeamMemberRequest;
import org.inventory.appuser.team.requests.CreateTeamRequest;
import org.inventory.appuser.team.requests.RemoveTeamMemberRequest;
import org.inventory.appuser.user.model.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTeam(@RequestBody CreateTeamRequest request,
                                                          @AuthenticationPrincipal AppUser appUser) {
        Map<String, Object> response = new HashMap<>();
        response.put("teamId", teamService.createTeam(request, appUser));
        response.put("message", "Team created successfully");

         return ResponseEntity.ok(response);
    }

    @PostMapping("/add-members")
    public ResponseEntity<?> addTeamMembers(@RequestBody AddTeamMemberRequest request,
                                            @AuthenticationPrincipal AppUser appUser) {
        teamService.addTeamMembers(request, appUser);
        return ResponseEntity.ok("Team added successfully");
    }

    @DeleteMapping("/remove-members")
    public ResponseEntity<?> removeTeamMembers(@RequestBody RemoveTeamMemberRequest request,
                                            @AuthenticationPrincipal AppUser appUser) {
        teamService.removeTeamMembers(request, appUser);
        return ResponseEntity.ok("Team member [%s] removed successfully".formatted(request.userEmail()));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer teamId, @AuthenticationPrincipal AppUser appUser) {
        teamService.deleteTeam(teamId, appUser);
        return ResponseEntity.ok("Team deleted successfully");
    }
}
