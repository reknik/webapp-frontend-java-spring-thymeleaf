package com.reknik.webAppDemoFrontEnd.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RoleService {

    private final WebClient webClient;

    public RoleService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<String>> getUserRoles() {
        return webClient.get()
                .uri("/user/roles")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
