package org.peter.auth.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    @JsonProperty("jwt_token")
    private String token;

    private String email;

    private String firstName;

    private String lastName;

    private String role;
}
