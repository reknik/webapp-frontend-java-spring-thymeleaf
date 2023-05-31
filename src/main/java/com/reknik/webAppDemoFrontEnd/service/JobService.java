package com.reknik.webAppDemoFrontEnd.service;

import com.reknik.webAppDemoFrontEnd.entity.dto.JobDTO;
import com.reknik.webAppDemoFrontEnd.entity.request.JobAddRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JobService {

    final
    WebClient webClient;

    public JobService(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<JobDTO> findJobsForEmployeeId(final long employeeId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/job/getByEmployeeId/{employeeId}")
                        .build(employeeId))
                .retrieve()
                .bodyToFlux(JobDTO.class);
    }

    public Mono<HttpStatus> save(final JobAddRequest job) {
        return webClient.post()
                .uri("/job/save")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(job), JobAddRequest.class)
                .retrieve()
                .bodyToMono(HttpStatus.class);
    }

    public Mono<HttpStatus> delete(final long jobId) {
        return webClient.delete()
                .uri("/job/deleteById?id=" + jobId)
                .retrieve()
                .bodyToMono(HttpStatus.class);
    }
}
