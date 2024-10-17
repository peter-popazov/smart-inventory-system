package org.inventory.appuser.team;

import lombok.RequiredArgsConstructor;
import org.inventory.appuser.team.requests.AddTeamMemberRequest;
import org.inventory.appuser.team.requests.CreateTeamRequest;
import org.inventory.appuser.team.requests.RemoveTeamMemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTeam(@RequestBody CreateTeamRequest request,
                                                          @PathVariable("emil") String email) {
        Map<String, Object> response = new HashMap<>();
        response.put("teamId", teamService.createTeam(request, email));
        response.put("message", "Team created successfully");

         return ResponseEntity.ok(response);
    }

    @PostMapping("/add-member/{email}")
    public ResponseEntity<?> addTeamMembers(@RequestBody AddTeamMemberRequest request,
                                            @PathVariable("email") String email) {
        teamService.addTeamMembers(request, email);
        return ResponseEntity.ok("Team added successfully");
    }

    @DeleteMapping("/remove-member/{email}")
    public ResponseEntity<?> removeTeamMembers(@RequestBody RemoveTeamMemberRequest request,
                                               @PathVariable("email") String email) {
        teamService.removeTeamMembers(request, email);
        return ResponseEntity.ok("Team member [%s] removed successfully".formatted(request.userEmail()));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer teamId, @PathVariable("email") String email) {
        teamService.deleteTeam(teamId, email);
        return ResponseEntity.ok("Team deleted successfully");
    }
}
