package com.management.product.config.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class ExceptionHandler implements ProblemHandling {

    @Override
    public boolean isCausalChainsEnabled() {
        return true;
    }

}
