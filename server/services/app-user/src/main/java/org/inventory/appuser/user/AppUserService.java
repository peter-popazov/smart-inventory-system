package org.inventory.appuser.user;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.inventory.appuser.exception.UserNotFoundException;
import org.inventory.appuser.user.helpers.AppUserResponse;
import org.inventory.appuser.user.helpers.UpdateUserRequest;
import org.inventory.appuser.user.model.AppUser;
import org.inventory.appuser.user.repos.AppUserRepository;
import org.springframework.stereotype.Service;

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

    public AppUserResponse getUser(String email, String userId) {
        AppUser appUser = repository.findByEmail(email).
                orElseThrow(() -> new UserNotFoundException("Cannot get user. User not found with email: " + email));

        if (appUser.getRegisteredUserId() != Integer.parseInt(userId)) {
            throw new RuntimeException("Not enough permissions to retrieve user");
        }
        return AppUserResponse.builder()
                .email(appUser.getEmail())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                // todo
                .role(null)
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
}
