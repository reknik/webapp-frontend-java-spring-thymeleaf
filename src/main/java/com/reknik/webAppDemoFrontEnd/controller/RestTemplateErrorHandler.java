package com.reknik.webAppDemoFrontEnd.controller;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {
  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return (
        response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
            || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    if (!response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
      throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText());
    }
  }
}
