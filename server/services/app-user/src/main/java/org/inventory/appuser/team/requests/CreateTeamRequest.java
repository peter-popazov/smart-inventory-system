package org.inventory.appuser.team.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CreateTeamRequest(

        @NotEmpty(message = "Team name is required")
        @NotBlank(message = "Team name is required")
        String teamName
) {
}
