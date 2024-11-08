package org.inventory.appuser.team.helpers;

import lombok.RequiredArgsConstructor;
import org.inventory.appuser.team.Team;
import org.inventory.appuser.team.dto.TeamResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamMapper {

    private final AppUserMapper userMapper;

    public TeamResponse toTeamResponse(Team team) {
        return TeamResponse.builder()
                .teamId(team.getTeamId())
                .teamName(team.getTeamName())
                .teamDescription(team.getTeamDescription())
                .members(
                        team.getTeamMembership().stream()
                                .map(tm -> userMapper.toAppUserResponse(tm.getAppUser()))
                                .toList()
                )
                .teamSize(team.getTeamMembership().size())
                .build();
    }
}
