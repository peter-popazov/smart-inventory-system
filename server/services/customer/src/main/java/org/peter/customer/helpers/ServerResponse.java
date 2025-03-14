package org.peter.customer.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerResponse<T> {

    @JsonProperty("response")
    private T response;
}
