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

                    Boolean isValid = validateToken(token, email);
                    if (!isValid) throw new BadCredentialsException("Invalid token");
                }
            }

            return chain.filter(exchange);
        }));
    }

    private Boolean validateToken(String token, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8010/validate-token?token=" + token + "&email=" + email;
//        Boolean isValid = restTemplate.getForObject(url, Boolean.class);
        ResponseEntity<Boolean> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Boolean.class
        );
        return Objects.equals(response.getBody(), Boolean.TRUE);
    }

    public static class Config {
    }
}
