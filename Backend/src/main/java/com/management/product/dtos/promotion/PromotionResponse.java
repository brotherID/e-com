package com.management.product.dtos.promotion;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PromotionResponse {
    private Long idPromotion;
    private String codePromotion;
    private Integer discountPercentage;
}
