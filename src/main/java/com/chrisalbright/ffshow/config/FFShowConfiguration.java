package com.chrisalbright.ffshow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FFShowConfiguration {

  @Bean()
  public WebClient omdbWebClient(OMDBConfiguration config) {
    return WebClient
        .builder()
        .baseUrl(config.getBaseUrl())
        .build();
  }
}
