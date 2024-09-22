package org.inventory.appuser.user;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.inventory.appuser.user.exception.UserNotFoundException;
import org.inventory.appuser.user.helpers.AppUserMapper;
import org.inventory.appuser.user.helpers.RegisterUserRequest;
import org.inventory.appuser.user.helpers.UpdateUserRequest;
import org.inventory.appuser.user.model.AppUser;
import org.inventory.appuser.user.repos.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;
    private final AppUserMapper mapper;

    public int registerUser(RegisterUserRequest request) {
        AppUser user = repository.save(mapper.toUser(request));
        return user.getUserId();
    }

    public Void updateUser(UpdateUserRequest request) {
        var user = repository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("Cannot update user. User not found with email: " + request.email()));
        mergeCustomer(user, request);
        repository.save(user);
        return null;
    }

    public AppUser getUser(String email) {
        return repository.findByEmail(email).
                orElseThrow(() -> new UserNotFoundException("Cannot get user. User not found with email: " + email));
    }

    private void mergeCustomer(AppUser user, UpdateUserRequest request) {
        if (StringUtils.isNotBlank(request.email())) {
            user.setEmail(request.email());
        }
        if (StringUtils.isNotBlank(request.firstName())) {
            user.setEmail(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
            user.setEmail(request.lastName());
        }
        if (StringUtils.isNotBlank(request.password())) {
            user.setEmail(request.password());
        }

    }

    public Boolean existsByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public Boolean deleteUser(String email) {
        if (!existsByEmail(email)) {
            return false;
        }
        repository.delete(getUser(email));
        return true;
    }
}
