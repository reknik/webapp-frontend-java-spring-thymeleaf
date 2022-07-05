package com.reknik.webAppDemoFrontEnd.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class HomeControllerExceptionHandler {

  @ExceptionHandler(value = HttpClientErrorException.class)
  ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException exception, HttpServletRequest request) {
    return new ResponseEntity<>("Operation failed", exception.getStatusCode());
  }
}
