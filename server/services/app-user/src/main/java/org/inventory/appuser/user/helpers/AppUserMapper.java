package org.inventory.appuser.user.helpers;

import org.inventory.appuser.user.model.AppUser;
import org.springframework.stereotype.Service;

@Service
public class AppUserMapper {

    public AppUser toUser(RegisterUserRequest request) {
        if (request == null) return null;

        return AppUser.builder()
                .email(request.email())
                .password(request.password())
                .build();
    }
}
