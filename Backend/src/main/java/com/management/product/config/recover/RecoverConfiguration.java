package com.management.product.config.recover;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Recover;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Configuration
@Slf4j
public class RecoverConfiguration {

    private int attempts ;

    @Recover
    public String recover(WebClientRequestException e) {
        attempts = 1;
        log.info("Recovering after retries failed: {}", e.getMessage());
        return "Service is temporarily unavailable";
    }
}
