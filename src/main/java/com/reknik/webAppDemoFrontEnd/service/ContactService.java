package com.reknik.webAppDemoFrontEnd.service;

import com.reknik.webAppDemoFrontEnd.entity.dto.ContactDTO;
import com.reknik.webAppDemoFrontEnd.entity.request.ContactAddRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ContactService {

    private final WebClient webClient;

    public ContactService(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<ContactDTO> findContactsForEmployeeId(final long employeeId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/contact/getByEmployeeId/{employeeId}")
                        .build(employeeId))
                .retrieve()
                .bodyToFlux(ContactDTO.class);
    }

    public Mono<HttpStatus> save(final ContactAddRequest contact) {
        return webClient.post()
                .uri("/contact/save")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(contact), ContactAddRequest.class)
                .retrieve()
                .bodyToMono(HttpStatus.class);
    }

    public Mono<HttpStatus> delete(final long contactId) {
        return webClient.delete()
                .uri("/contact/deleteById?id=" + contactId)
                .retrieve()
                .bodyToMono(HttpStatus.class);
    }
}
