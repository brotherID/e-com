package com.management.product.service.promotion;

import com.management.product.dtos.promotion.PromotionRequest;
import com.management.product.dtos.promotion.PromotionResponse;
import com.management.product.entities.cart.Cart;
import com.management.product.entities.product.ShoppedProduct;
import com.management.product.entities.promotion.Promotion;
import com.management.product.mapper.product.ProductMapper;
import com.management.product.mapper.promotion.PromotionMapper;
import com.management.product.repository.cart.product.ShoppedProductRepository;
import com.management.product.repository.cart.user.CartRepository;
import com.management.product.repository.product.ProductRepository;
import com.management.product.repository.promotion.PromotionRepository;
import com.management.product.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.zalando.problem.Status.NOT_FOUND;


@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionServiceImpl implements PromotionService {

    public static final String PROMOTION_DOES_NOT_EXIST = "Promotion does not exist !";
    public static final String PROMOTION_WITH_ID_DOES_NOT_EXIST = "Promotion with id %d  does not exist";
    public static final String CODE_PROMOTION_DOES_NOT_EXIST1 = "Code promotion does not exist";
    public static final String CODE_PROMOTION_DOES_NOT_EXIST = "Code promotion with %s does not exist";
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;
    private final CartRepository cartRepository;
    private final ShoppedProductRepository shoppedProductRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CartService cartService;

    @Override
    public PromotionResponse addPromotion(PromotionRequest promotionRequest) {
        Promotion promotion = promotionRepository.save(promotionMapper.toPromotion(promotionRequest));
        return promotionMapper.toPromotionResponse(promotion);
    }

    @Override
    public PromotionResponse updatePromotionById(Long idPromotion, PromotionRequest promotionRequest) {
        Promotion promotion = promotionRepository.findById(idPromotion)
                .orElseThrow(
                        () -> Problem.builder()
                                .withTitle(PROMOTION_DOES_NOT_EXIST)
                                .withStatus(NOT_FOUND)
                                .withDetail(String.format(PROMOTION_WITH_ID_DOES_NOT_EXIST, idPromotion))
                                .build()
                );
        promotionMapper.updateEntity(promotion, promotionRequest);
        return promotionMapper.toPromotionResponse(promotionRepository.save(promotion));
    }

    @Override
    public Optional<PromotionResponse> getPromotionByCodePromotion(String codePromotion) {
        return promotionRepository.findByCodePromotion(codePromotion)
                .map(promotionMapper::toPromotionResponse);
    }

    @Override
    public void removePromotionById(Long idPromotion) {
        Promotion promotion = promotionRepository.findById(idPromotion)
                .orElseThrow(
                        () -> Problem.builder()
                                .withTitle(PROMOTION_DOES_NOT_EXIST)
                                .withStatus(NOT_FOUND)
                                .withDetail(String.format(PROMOTION_WITH_ID_DOES_NOT_EXIST, idPromotion))
                                .build()
                );
        promotionRepository.deleteById(idPromotion);
    }

    @Override
    public List<PromotionResponse> getAllPromotions() {
        return promotionMapper.toPromotionResponseList(promotionRepository.findAll());
    }

    @Override
    public BigDecimal getDiscount(String codePromotion) {
        Promotion promotion = promotionRepository.findByCodePromotion(codePromotion)
                .orElseThrow(
                        () -> Problem.builder()
                                .withTitle(CODE_PROMOTION_DOES_NOT_EXIST1)
                                .withStatus(NOT_FOUND)
                                .withDetail(String.format(CODE_PROMOTION_DOES_NOT_EXIST, codePromotion))
                                .build()
                );
        Cart cart = cartService.getCartOfAuthenticatedUser();
        List<ShoppedProduct> shoppedProducts = Optional.of(cart)
                .map(Cart::getIdCart)
                .map(shoppedProductRepository::findByIdCart)
                .orElse(List.of());
        BigDecimal totalAmount = cartService.calculateTotalAmountProductsWithPromotion(shoppedProducts, Optional.of(promotion));
        cart.setTotalAmount(totalAmount);
        cart.setCodePromotion(promotion.getCodePromotion());
        cartService.save(cart);
        return totalAmount;
    }

    @Override
    public Optional<Promotion> findPromotionByCodePromotion(String codePromotion) {
        return promotionRepository.findByCodePromotion(codePromotion);
    }

}
