package org.inventory.appuser.team.dto;

import lombok.Builder;
import org.inventory.appuser.user.helpers.AppUserResponse;

import java.util.List;

@Builder
public record TeamResponse(
        Integer teamId,
        String teamName,
        String teamDescription,
        List<AppUserResponse> members,
        Integer teamSize
) {
}
