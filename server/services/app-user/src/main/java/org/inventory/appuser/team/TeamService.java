package org.inventory.appuser.team;

import lombok.RequiredArgsConstructor;
import org.inventory.appuser.exception.*;
import org.inventory.appuser.team.requests.AddTeamMemberRequest;
import org.inventory.appuser.team.requests.CreateTeamRequest;
import org.inventory.appuser.team.requests.RemoveTeamMemberRequest;
import org.inventory.appuser.user.model.AppUser;
import org.inventory.appuser.user.model.Role;
import org.inventory.appuser.user.repos.AppUserRepository;
import org.inventory.appuser.user.repos.RoleRepository;
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


    public Integer createTeam(CreateTeamRequest request, String email) {
        Team team = teamRepository.save(
                Team.builder()
                        .teamName(request.teamName())
                        .build());

        // todo ??ROLE_
        Role userTeamRole = roleRepository.findByName("ADMIN").orElseGet(
                () -> roleRepository.save(Role.builder().name("ADMIN").build())
        );

        AppUser appUser = getAppuser(email);

        AppUser teamOwner = appUserRepository.findById(appUser.getUserId()).
                orElseThrow(() -> new UserNotFoundException("User not found with id: " + appUser.getUserId()));

        TeamMembership teamMembership = teamMembershipRepository.save(
                TeamMembership.builder()
                        .team(team)
                        .role(userTeamRole)
                        .appUser(teamOwner)
                        .build()
        );

        team.getTeamMembership().add(teamMembership);
        return teamRepository.save(team).getTeamId();
    }

    public void addTeamMembers(AddTeamMemberRequest request, String email) {

        Team team = teamRepository.findById(request.teamId()).orElseThrow(() ->
                new TeamNotFoundException("Team with entered ID not found: " + request.teamId()));

        AppUser appUser = getAppuser(email);

        validateUserTeamOwnership(appUser, team);

        AppUser appUserAddingToTeam = appUserRepository.findByEmail(request.userEmail())
                .orElseThrow(() -> new UserNotFoundException("User with entered email not found"));

        if (appUser.getUserId().equals(appUserAddingToTeam.getUserId())) {
            throw new AlreadyInTeamException("You are already in a team");
        }

        boolean isAlreadyMember = team.getTeamMembership().stream()
                .anyMatch(teamMembership -> Objects.equals(teamMembership.getAppUser().getUserId(), appUserAddingToTeam.getUserId()));

        if (isAlreadyMember) {
            throw new AlreadyInTeamException("User is already a member of this team");
        }

        Role appUserRoleAddingToTeam = roleRepository.findByName(request.role())
                .orElseGet(() -> roleRepository.save(Role.builder().name(request.role()).build()));

        TeamMembership userTeamMembership = TeamMembership.builder()
                .appUser(appUserAddingToTeam)
                .role(appUserRoleAddingToTeam)
                .team(team)
                .build();

        team.getTeamMembership().add(userTeamMembership);
        teamMembershipRepository.save(userTeamMembership);
    }

    // todo
    @Transactional
    public void deleteTeam(Integer teamId, String email) {

        Team team = teamRepository.findById(teamId).orElseThrow(() ->
                new TeamNotFoundException("Team with entered ID not found: " + teamId));

        AppUser appUser = getAppuser(email);

        validateUserTeamOwnership(appUser, team);

        List<TeamMembership> memberships = team.getTeamMembership();
        for (TeamMembership membership : memberships) {
            membership.setTeam(null);
            membership.setAppUser(null);
            membership.setRole(null);

            teamMembershipRepository.delete(membership);
        }

        teamRepository.delete(team);
    }

    private void validateUserTeamOwnership(AppUser appUser, Team team) {
        appUser.getTeamMembership().stream()
                .filter(teamMembership -> teamMembership.getTeam().getTeamId() == team.getTeamId())
                .findFirst()
                .orElseThrow(() -> new InsufficientPrivilegesException("Current user cannot make changes to the team they do not own"));

    }

    public void removeTeamMembers(RemoveTeamMemberRequest request, String email) {

        Team team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new TeamNotFoundException("Team with entered ID not found: " + request.teamId()));

        AppUser appUser = getAppuser(email);

        validateUserTeamOwnership(appUser, team);

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

    private AppUser getAppuser(String email) {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
}
