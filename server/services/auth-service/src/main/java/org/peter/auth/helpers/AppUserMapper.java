package org.peter.auth.helpers;

import org.peter.auth.model.AppUser;
import org.springframework.stereotype.Service;

@Service
public class AppUserMapper {

    public AppUser toUser(RegisterRequest request) {
        if (request == null) return null;

        return AppUser.builder()
                .email(request.email())
                .password(request.password())
                .build();
    }
}
