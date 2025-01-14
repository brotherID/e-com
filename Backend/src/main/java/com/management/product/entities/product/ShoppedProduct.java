package com.management.product.entities.product;


import com.management.product.entities.base.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ShoppedProduct  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idShoppedProduct;
    private Long idCart;
    private Long idProduct;
    private Integer quantityRequested;
}
