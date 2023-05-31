package com.reknik.webAppDemoFrontEnd.service;

import com.reknik.webAppDemoFrontEnd.entity.dto.AddressDTO;
import com.reknik.webAppDemoFrontEnd.entity.request.AddressAddRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AddressService {

    private final WebClient webClient;

    public AddressService(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<AddressDTO> findAddressesForEmployeeId(final long employeeId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/address/getByEmployeeId/{employeeId}")
                        .build(employeeId))
                .retrieve()
                .bodyToFlux(AddressDTO.class);
    }

    public Mono<HttpStatus> save(final AddressAddRequest address) {
        return webClient.post()
                .uri("/address/save")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(address), AddressAddRequest.class)
                .retrieve()
                .bodyToMono(HttpStatus.class);
    }

    public Mono<HttpStatus> delete(final long addressId) {
        return webClient.delete()
                .uri("/address/deleteById?id=" + addressId)
                .retrieve()
                .bodyToMono(HttpStatus.class);
    }
}
