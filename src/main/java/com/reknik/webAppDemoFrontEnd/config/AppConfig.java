package com.reknik.webAppDemoFrontEnd.config;

import com.reknik.webAppDemoFrontEnd.controller.RestTemplateErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  private final RestTemplateErrorHandler restTemplateErrorHandler;

  @Autowired
  public AppConfig(RestTemplateErrorHandler restTemplateErrorHandler) {
    this.restTemplateErrorHandler = restTemplateErrorHandler;
  }

  @Bean
  public RestTemplate getRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(restTemplateErrorHandler);
    return restTemplate;
  }
}
