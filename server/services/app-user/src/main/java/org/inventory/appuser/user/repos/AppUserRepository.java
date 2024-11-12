package org.inventory.appuser.user.repos;

import org.inventory.appuser.user.model.AppUser;
import org.inventory.appuser.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByRegisteredUserId(Integer registeredUserId);

    @Query("SELECT tm.role FROM TeamMembership tm " +
            "WHERE tm.appUser.userId = :userId")
    Optional<Role> findUserRole(@Param("userId") Integer userId);
}
