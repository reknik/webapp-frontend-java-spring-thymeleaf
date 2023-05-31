package com.reknik.webAppDemoFrontEnd.service;

import com.reknik.webAppDemoFrontEnd.entity.dto.CompanyDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class CompanyService {

    private final WebClient webClient;

    public CompanyService(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<CompanyDTO> findAll() {
        return webClient.get().uri("/company/getAll")
                .retrieve().bodyToFlux(CompanyDTO.class);
    }
}
