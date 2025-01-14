package com.management.product.dtos.product;

import com.management.product.enums.InventoryStatus;
import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long idProduct;
    private String   codeProduct;
    private String   nameProduct;
    private String quantityProduct;
    private BigDecimal priceProduct;
}
