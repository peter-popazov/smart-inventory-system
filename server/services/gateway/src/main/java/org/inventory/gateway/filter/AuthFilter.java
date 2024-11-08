package org.inventory.gateway.filter;

import org.inventory.gateway.exceptions.BadCredentialsException;
import org.inventory.gateway.token.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final RouteValidator validator;
    private final RestTemplate restTemplate;
    private final JwtService jwtService;

    @Autowired
    public AuthFilter(RouteValidator validator, RestTemplate restTemplate, JwtService jwtService) {
        super(Config.class);
        this.validator = validator;
        this.restTemplate = restTemplate;
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = null;
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new BadCredentialsException("Unauthorized");
                }

                final String authHeaders = Objects.requireNonNull(exchange.getRequest().getHeaders()
                        .get(HttpHeaders.AUTHORIZATION)).get(0);

                String token;
                if (authHeaders.startsWith("Bearer ")) {
                    token = authHeaders.substring(7);
                    String email = jwtService.extractUsername(token);

                    Integer loggedInUserId = validateToken(token, email);
                    Integer adminId = getTeamAdminId(token, String.valueOf(loggedInUserId));
                    request = exchange.getRequest()
                            .mutate()
                            .header("loggedInUserId", String.valueOf(loggedInUserId))
                            .header("teamAdminId", String.valueOf(adminId != null ? adminId : ""))
                            .build();
                }
            }

            assert request != null;
            return chain.filter(exchange.mutate().request(request).build());
        }));
    }

    private Integer validateToken(String token, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8010/api/v1/auth/validate-token?token=" + token + "&email=" + email;
        ResponseEntity<Integer> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Integer.class
        );
        return response.getBody();
    }

    private Integer getTeamAdminId(String token, String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8090/api/v1/teams/" + userId;
        ResponseEntity<Integer> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Integer.class
        );
        return response.getBody();
    }


    public static class Config {
    }
}
