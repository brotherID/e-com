package com.management.product.service;

import com.management.product.dtos.offer.OfferResponse;

import java.util.Optional;

public interface OfferService {
    OfferResponse getOfferForProduct(String idOffer);
}
