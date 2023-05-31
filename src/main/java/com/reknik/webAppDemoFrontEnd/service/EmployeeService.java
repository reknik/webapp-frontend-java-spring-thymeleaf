package com.reknik.webAppDemoFrontEnd.service;

import com.reknik.webAppDemoFrontEnd.entity.dto.EmployeeDTO;
import com.reknik.webAppDemoFrontEnd.entity.request.EmployeeAddRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    private final WebClient webClient;

    public EmployeeService(final WebClient webClient) {
        this.webClient = webClient;
    }


    public Flux<EmployeeDTO> findAll() {
        return webClient.get().uri("/employee/getAll")
                .retrieve()
                .bodyToFlux(EmployeeDTO.class);
    }

    public Mono<HttpStatus> save(final EmployeeAddRequest employee) {
        return webClient.post()
                .uri("/employee/save")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(employee), EmployeeAddRequest.class)
                .retrieve()
                .bodyToMono(HttpStatus.class);
    }

    public Mono<EmployeeDTO> findById(final long id) {
        return webClient.get().uri("/employee/" + id)
                .retrieve()
                .bodyToMono(EmployeeDTO.class);
    }

    public Mono<HttpStatus> update(final EmployeeAddRequest employee) {
        return webClient.put()
                .uri("/employee/update")
                .body(BodyInserters.fromValue(employee))
                .retrieve()
                .bodyToMono(HttpStatus.class);
    }

    public Mono<HttpStatus> deleteById(final int id) {
        return webClient.delete()
                .uri("/employee/deleteById?id=" + id)
                .retrieve()
                .bodyToMono(HttpStatus.class);
    }
}
