package com.management.product.web;

import com.management.product.service.OfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/offers")
@Slf4j
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/{idOffer}")

    public ResponseEntity<?> getPromotionsForProduct(@PathVariable(name = "idOffer")  String idOffer) {
        log.info("Begin getPromotionsForProduct ");
        return ResponseEntity.ok(offerService.getOfferForProduct(idOffer));
    }

}
