package com.management.product.web.promotion;

import com.management.product.dtos.promotion.PromotionRequest;
import com.management.product.dtos.promotion.PromotionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


public interface PromotionController {
    String PATH_VARIABLE_CODE_PROMOTION = "codePromotion";
    String PATH_VARIABLE_ID_PROMOTION = "idPromotion";
    String URI_CODE_PROMOTION = "/{codePromotion}";
    String URI_ID_PROMOTION = "/{idPromotion}";
    String URI_DISCOUNT = "/discount";

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    ResponseEntity<PromotionResponse> addPromotion(@RequestBody PromotionRequest promotionRequest);
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = URI_CODE_PROMOTION)
    ResponseEntity<PromotionResponse> getProductByCodePromotion(@PathVariable(name = PATH_VARIABLE_CODE_PROMOTION) String codePromotion);
    @CrossOrigin(origins = "http://localhost:3000")
    @PatchMapping(value = URI_ID_PROMOTION)
    ResponseEntity<PromotionResponse> updateProductById(@PathVariable(name = PATH_VARIABLE_ID_PROMOTION) Long idPromotion , @RequestBody PromotionRequest promotionRequest);
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value = URI_ID_PROMOTION)
    ResponseEntity<Void> deleteProductById(@PathVariable(name = PATH_VARIABLE_ID_PROMOTION) Long idPromotion);
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    ResponseEntity<List<PromotionResponse>> getAllPromotions() ;
    @GetMapping(value= URI_DISCOUNT+URI_CODE_PROMOTION)
    ResponseEntity<BigDecimal> getDiscount(@PathVariable(name = PATH_VARIABLE_CODE_PROMOTION) String codePromotion);
}
