package com.chrisalbright.ffshow.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
public class OMDBConfiguration {
  private String apiKey;
  private String baseUrl;
}
