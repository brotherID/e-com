package com.management.product.repository.promotion;

import com.management.product.entities.promotion.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion,Long> {
    Optional<Promotion> findByCodePromotion(String codePromotion);
}
