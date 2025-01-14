package com.management.product.service.promotion;

import com.management.product.dtos.promotion.PromotionRequest;
import com.management.product.dtos.promotion.PromotionResponse;
import com.management.product.entities.promotion.Promotion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PromotionService {
    PromotionResponse addPromotion(PromotionRequest promotionRequest);
    PromotionResponse updatePromotionById(Long idPromotion, PromotionRequest promotionRequest);
    Optional<PromotionResponse> getPromotionByCodePromotion(String codePromotion);
    void removePromotionById(Long idPromotion);
    List<PromotionResponse> getAllPromotions();
    BigDecimal getDiscount(String codePromotion);
    Optional<Promotion> findPromotionByCodePromotion(String codePromotion);
}
