package com.management.product.dtos.promotion;


import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PromotionRequest {
    private String codePromotion;
    private Integer discountPercentage;
}
