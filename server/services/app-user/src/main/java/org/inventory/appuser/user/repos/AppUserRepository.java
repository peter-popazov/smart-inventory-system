package org.inventory.appuser.user.repos;

import org.inventory.appuser.user.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByRegisteredUserId(Integer registeredUserId);
}
