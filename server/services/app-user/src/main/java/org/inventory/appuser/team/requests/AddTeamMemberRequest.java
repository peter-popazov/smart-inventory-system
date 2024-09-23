package org.inventory.appuser.team.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record AddTeamMemberRequest(

        @NotEmpty(message = "Email is required")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid")
        String userEmail,

        @NotEmpty(message = "Role is required")
        @NotBlank(message = "Role is required")
        String role,

        @NotEmpty(message = "Team id is required")
        @NotBlank(message = "Team id is required")
        Integer teamId

) {
}
