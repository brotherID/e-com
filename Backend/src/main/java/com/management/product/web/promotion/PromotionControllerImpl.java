package com.management.product.web.promotion;

import com.management.product.dtos.promotion.PromotionRequest;
import com.management.product.dtos.promotion.PromotionResponse;
import com.management.product.service.promotion.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zalando.problem.Problem;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static org.zalando.problem.Status.NOT_FOUND;

@RestController
@RequestMapping("api/v1/promotions")
@RequiredArgsConstructor
public class PromotionControllerImpl implements  PromotionController{

    public static final String PROMOTION_DOES_NOT_EXIST = "Promotion does not exist";
    public static final String THE_CODE_PROMOTION_DOES_NOT_EXIST = "The code promotion %s does not exist ";
    private  final PromotionService promotionService;

    @Override
    public ResponseEntity<PromotionResponse> addPromotion(PromotionRequest promotionRequest) {
        PromotionResponse promotionResponse =  promotionService.addPromotion(promotionRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(promotionResponse.getIdPromotion())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(promotionResponse);
    }

    @Override
    public ResponseEntity<PromotionResponse> getProductByCodePromotion(String codePromotion) {
        return promotionService.getPromotionByCodePromotion(codePromotion)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> Problem.builder()
                        .withTitle(PROMOTION_DOES_NOT_EXIST)
                        .withStatus(NOT_FOUND)
                        .withDetail(String.format(THE_CODE_PROMOTION_DOES_NOT_EXIST, codePromotion))
                        .build());
    }

    @Override
    public ResponseEntity<PromotionResponse> updateProductById(Long idPromotion, PromotionRequest promotionRequest) {
        return ResponseEntity.ok(promotionService.updatePromotionById(idPromotion,promotionRequest));
    }

    @Override
    public ResponseEntity<Void> deleteProductById(Long idPromotion) {
        promotionService.removePromotionById(idPromotion);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<PromotionResponse>> getAllPromotions() {
        return  ResponseEntity.ok(promotionService.getAllPromotions());
    }

    @Override
    public ResponseEntity<BigDecimal> getDiscount(String codePromotion) {
        return ResponseEntity.ok(promotionService.getDiscount(codePromotion));
    }
}
