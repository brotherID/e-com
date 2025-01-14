package com.management.product.config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class ProblemConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModules(
                new ProblemModule(),
                new ConstraintViolationProblemModule())
                .registerModules(new JavaTimeModule());
    }

}

