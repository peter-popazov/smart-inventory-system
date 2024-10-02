package org.inventory.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> endpoints = List.of(
            "/api/v1/auth/register",
            "/api/v1/auth/authenticate",
            "/api/v1/auth/validate-token",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            req -> endpoints.stream()
                    .noneMatch(uri -> req.getURI().getPath().contains(uri));
}
