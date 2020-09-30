package com.chrisalbright.ffshow.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
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

  @Bean
  @ConfigurationProperties(prefix = "omdb")
  public OMDBConfiguration omdbConfiguration() {
    return new OMDBConfiguration();
  }

  @Bean
  @ConfigurationProperties(prefix = "security")
  public SecurityConfiguration securityConfiguration() {
    return new SecurityConfiguration();
  }
}
