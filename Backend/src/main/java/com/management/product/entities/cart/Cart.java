package com.management.product.entities.cart;

import com.management.product.entities.base.Auditable;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCart;
    private LocalDateTime expirationDate;
    @Column(unique = true)
    private String email;
    private BigDecimal totalAmount;
    private String codePromotion;
}
