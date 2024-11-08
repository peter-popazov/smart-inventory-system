package org.inventory.appuser.team.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RemoveTeamMemberRequest(

        @NotEmpty(message = "Email is required")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid")
        String userEmail,

        @NotEmpty(message = "Team id is required")
        @NotBlank(message = "Team id is required")
        Integer teamId

) {
}
