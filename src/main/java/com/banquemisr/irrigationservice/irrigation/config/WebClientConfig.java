package com.banquemisr.irrigationservice.irrigation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${irrigation.api.base-url}")
    private String irrigationApiBaseUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(irrigationApiBaseUrl).build();
    }
}
