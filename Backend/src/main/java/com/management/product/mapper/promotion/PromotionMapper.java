package com.management.product.mapper.promotion;

import com.management.product.dtos.promotion.PromotionRequest;
import com.management.product.dtos.promotion.PromotionResponse;
import com.management.product.entities.promotion.Promotion;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    @Mapping(source = "idPromotion", target = "idPromotion")
    PromotionResponse toPromotionResponse(Promotion promotion);
    Promotion  toPromotion(PromotionRequest promotionRequest);
    List<PromotionResponse> toPromotionResponseList(List<Promotion> promotions);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Promotion promotion, PromotionRequest promotionRequest);
}
