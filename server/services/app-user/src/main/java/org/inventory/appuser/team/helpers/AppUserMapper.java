package org.inventory.appuser.team.helpers;

import lombok.RequiredArgsConstructor;
import org.inventory.appuser.user.helpers.AppUserResponse;
import org.inventory.appuser.user.model.AppUser;
import org.inventory.appuser.user.model.Role;
import org.inventory.appuser.user.repos.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserMapper {

    private final AppUserRepository appUserRepository;

    public AppUserResponse toAppUserResponse(AppUser user) {
        Role userRole = appUserRepository.findUserRole(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User role not found"));

        return AppUserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(userRole.getName())
                .build();
    }
}
