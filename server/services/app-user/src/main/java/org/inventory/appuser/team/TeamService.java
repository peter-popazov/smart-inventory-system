package org.inventory.appuser.team;

import lombok.RequiredArgsConstructor;
import org.inventory.appuser.exception.*;
import org.inventory.appuser.team.dto.AddTeamMemberRequest;
import org.inventory.appuser.team.dto.CreateTeamRequest;
import org.inventory.appuser.team.dto.RemoveTeamMemberRequest;
import org.inventory.appuser.team.dto.TeamResponse;
import org.inventory.appuser.team.helpers.TeamMapper;
import org.inventory.appuser.user.model.AppUser;
import org.inventory.appuser.user.model.Role;
import org.inventory.appuser.user.repos.AppUserRepository;
import org.inventory.appuser.user.repos.RoleRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final TeamRepository teamRepository;
    private final TeamMembershipRepository teamMembershipRepository;
    private final TeamMapper teamMapper;

    public List<TeamResponse> getTeams(String loggedInUserId) {
        List<Team> teams = teamMembershipRepository.findTeamsByRegisteredUserIdAndRole(Integer.parseInt(loggedInUserId));
        return teams.stream()
                .map(teamMapper::toTeamResponse)
                .toList();
    }

    public void createTeam(CreateTeamRequest request, String loggedInUserId) {

        Role userTeamRole = roleRepository.findByName(TeamRoles.ADMIN.name()).orElseGet(
                () -> roleRepository.save(Role.builder().name(TeamRoles.ADMIN.name()).build())
        );

        AppUser teamOwner = getAppuser(loggedInUserId);

        Team team = Team.builder()
                .teamName(request.teamName())
                .teamDescription(request.teamDescription())
                .build();

        TeamMembership teamMembership = TeamMembership.builder()
                .team(team)
                .role(userTeamRole)
                .appUser(teamOwner)
                .build();

        team.setTeamMembership(List.of(teamMembership));

        teamRepository.save(team);
    }

    public void addTeamMembers(AddTeamMemberRequest request, String loggedInUserId) {

        Team team = teamRepository.findById(request.teamId()).orElseThrow(() ->
                new TeamNotFoundException("Team with entered ID not found: " + request.teamId()));

        AppUser appUser = getAppuser(loggedInUserId);

        validateUserTeamOwnership(appUser.getTeamMembership(), team.getTeamId());

        AppUser appUserAddingToTeam = appUserRepository.findByEmail(request.userEmail())
                .orElseThrow(() -> new UserNotFoundException("User with entered email not found"));

        if (appUser.getUserId().equals(appUserAddingToTeam.getUserId())) {
            throw new AlreadyInTeamException("User is already in a team");
        }

        boolean isAlreadyMember = team.getTeamMembership().stream()
                .anyMatch(teamMembership -> Objects.equals(teamMembership.getAppUser().getUserId(), appUserAddingToTeam.getUserId()));

        if (isAlreadyMember) {
            throw new AlreadyInTeamException("User is already a member of this team");
        }

        Role appUserRoleAddingToTeam = roleRepository.findByName(request.role().toUpperCase())
                .orElseGet(() -> roleRepository.save(Role.builder().name(request.role().toUpperCase()).build()));

        TeamMembership userTeamMembership = TeamMembership.builder()
                .appUser(appUserAddingToTeam)
                .role(appUserRoleAddingToTeam)
                .team(team)
                .build();

        team.getTeamMembership().add(userTeamMembership);
        teamMembershipRepository.save(userTeamMembership);
    }

    @Transactional
    public void deleteTeam(Integer teamId, String loggedInUserId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() ->
                new TeamNotFoundException("Team with entered ID not found: " + teamId));

        AppUser appUser = getAppuser(loggedInUserId);

        validateUserTeamOwnership(appUser.getTeamMembership(), team.getTeamId());

        List<TeamMembership> memberships = team.getTeamMembership();
        for (TeamMembership membership : memberships) {
            membership.setTeam(null);
            membership.setAppUser(null);
            membership.setRole(null);

            teamMembershipRepository.delete(membership);
        }

        teamRepository.delete(team);
    }

    private void validateUserTeamOwnership(List<TeamMembership> userTeamMemberships, Integer teamId) {
        userTeamMemberships.stream()
                .filter(teamMembership -> teamMembership.getTeam().getTeamId() == teamId)
                .findFirst()
                .orElseThrow(() -> new InsufficientPrivilegesException("Current user cannot make changes to the team they do not own"));

    }

    public void removeTeamMembers(RemoveTeamMemberRequest request, String loggedInUserId) {

        Team team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new TeamNotFoundException("Team with entered ID not found: " + request.teamId()));

        AppUser appUser = getAppuser(loggedInUserId);

        validateUserTeamOwnership(appUser.getTeamMembership(), team.getTeamId());

        AppUser removeUserMembership = appUserRepository.findByEmail(request.userEmail())
                .orElseThrow(() -> new UserNotFoundException("User with entered email not found"));

        TeamMembership membershipToRemove = removeUserMembership.getTeamMembership().stream()
                .filter(teamMembership -> teamMembership.getTeam().equals(team))
                .findFirst()
                .orElseThrow(() -> new NotTeamMemberException("User [%s] is not a member of the team with id [%d]"
                        .formatted(request.userEmail(), request.teamId())));

        removeUserMembership.getTeamMembership().remove(membershipToRemove);
        team.getTeamMembership().remove(membershipToRemove);

        teamMembershipRepository.delete(membershipToRemove);
    }

    private AppUser getAppuser(String loggedInUserId) {
        return appUserRepository.findByRegisteredUserId(Integer.parseInt(loggedInUserId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public Integer getAdminIdForUserTeam(Integer loggedInUserId) {
        AppUser appUser = appUserRepository.findByRegisteredUserId(loggedInUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return teamRepository.findAll().stream()
                .filter(team -> team.getTeamMembership().stream()
                        .anyMatch(tm -> tm.getAppUser() != null &&
                                tm.getAppUser().getUserId().equals(appUser.getUserId())))
                .flatMap(team -> team.getTeamMembership().stream())
                .filter(TeamMembership::isUserAdmin)
                .map(tm -> tm.getAppUser().getUserId())
                .findFirst()
                .orElse(null);
    }

    public Integer getTeamsSize(String userId) {
        return teamMembershipRepository.countTotalTeamMembersByUserId(Integer.parseInt(userId));
    }
}


