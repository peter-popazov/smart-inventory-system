package org.inventory.appuser.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Integer> {

    @Query("SELECT tm.team FROM TeamMembership tm WHERE tm.appUser.registeredUserId = :registeredUserId")
    List<Team> findTeamsByRegisteredUserIdAndRole(@Param("registeredUserId") Integer registeredUserId);

    @Query("SELECT SUM(size(t.teamMembership)) FROM Team t JOIN t.teamMembership tm WHERE tm.appUser.registeredUserId = :userId AND tm.role.name = 'ADMIN'")
    Integer countTotalTeamMembersByUserId(@Param("userId") Integer userId);
}
