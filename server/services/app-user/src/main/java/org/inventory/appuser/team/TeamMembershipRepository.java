package org.inventory.appuser.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Integer> {

    @Query("SELECT tm.team FROM TeamMembership tm WHERE tm.appUser.registeredUserId = :registeredUserId AND tm.role.name = 'ADMIN'")
    List<Team> findTeamsByRegisteredUserIdAndRole(@Param("registeredUserId") Integer registeredUserId);
}
