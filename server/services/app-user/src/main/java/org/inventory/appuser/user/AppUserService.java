package org.inventory.appuser.user;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.inventory.appuser.exception.UserNotFoundException;
import org.inventory.appuser.team.TeamMembership;
import org.inventory.appuser.user.helpers.AppUserResponse;
import org.inventory.appuser.user.helpers.UpdateUserRequest;
import org.inventory.appuser.user.model.AppUser;
import org.inventory.appuser.user.model.Role;
import org.inventory.appuser.user.repos.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;

    public void registerUser(Integer userId, String email) {
        repository.save(AppUser.builder()
                .registeredUserId(userId)
                .email(email)
                .build());
    }

    public Void updateUser(UpdateUserRequest request, String userId) {
        AppUser user = repository.findByRegisteredUserId(Integer.parseInt(userId))
                .orElseThrow(() -> new UserNotFoundException("Cannot update user. User not found"));

        if (user.getRegisteredUserId() != Integer.parseInt(userId)) {
            throw new RuntimeException("Cannot update user");
        }

        mergeUser(user, request);
        repository.save(user);
        return null;
    }

    public AppUserResponse getUserByEmail(String email) {
        AppUser appUser = repository.findByEmail(email).
                orElseThrow(() -> new UserNotFoundException("Cannot get user. User not found with email: " + email));

        return AppUserResponse.builder()
                .email(appUser.getEmail())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .role(repository.findUserRole(appUser.getUserId())
                        .orElse(Role.builder().name("USER").build()).getName())
                .build();
    }

    public AppUserResponse getUser(String loggedInUserId) {
        AppUser appUser = repository.findByRegisteredUserId(Integer.parseInt(loggedInUserId))
                .orElseThrow(() -> new UserNotFoundException("Cannot get user. User not found"));

        return AppUserResponse.builder()
                .email(appUser.getEmail())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .role(repository.findUserRole(appUser.getUserId())
                        .orElse(Role.builder().name("USER").build()).getName())
                .build();
    }

    private void mergeUser(AppUser user, UpdateUserRequest request) {
        if (StringUtils.isNotBlank(request.firstName())) {
            user.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
            user.setLastName(request.lastName());
        }
    }

    public Boolean existsByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public Boolean deleteUser(String email, String userId) {
        AppUser appUser = repository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Cannot delete user. User not found with email: " + email)
        );

        if (appUser.getRegisteredUserId() != Integer.parseInt(userId)) {
            throw new RuntimeException("Not enough permissions to delete user");
        }

        repository.delete(appUser);
        return true;
    }

    public List<Integer> getUsersByTeamId(String loggedInUserId) {
        AppUser appUser = repository.findByRegisteredUserId(Integer.parseInt(loggedInUserId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<TeamMembership> teamMemberships = appUser.getTeamMembership();

        return teamMemberships.stream()
                .map(tm -> tm.getAppUser().getUserId())
                .toList();
    }
}
