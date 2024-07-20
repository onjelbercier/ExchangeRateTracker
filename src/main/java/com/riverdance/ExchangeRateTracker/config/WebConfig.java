package com.riverdance.ExchangeRateTracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Function;

@EnableScheduling
@Configuration
public class WebConfig {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Bean("myBean")
    public Function<String, WebClient> webClientFunction(){
        return fullUri ->  webClientBuilder.baseUrl(fullUri).build();
    }
}
