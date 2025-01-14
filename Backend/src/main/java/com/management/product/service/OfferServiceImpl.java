package com.management.product.service;

import com.management.product.dtos.offer.OfferResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Service
@Slf4j
public class OfferServiceImpl  implements  OfferService{


    private int attempts =1;

    private final WebClient webClient;



    public OfferServiceImpl(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.baseUrl("http://localhost:8820/api/v1/offers").build();
    }

    @Retryable(
            retryFor  = {WebClientRequestException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    @Override
    public OfferResponse getOfferForProduct(String idOffer) {
        log.info("Begin getPromotionsForProduct : {} ",attempts++);
        return webClient.get()
                .uri("/{idOffer}", idOffer)
                .retrieve()
                .bodyToMono(OfferResponse.class)
                .block();
    }
}
