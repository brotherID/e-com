package com.management.product.dtos.offer;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponse {
    private String idOffer;
    private String productId;
    private Integer discountRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean status;
}
