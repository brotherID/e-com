package com.management.product.dtos.product;

import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShoppedProductResponse {
    private Long idProduct;
    private String codeProduct;
    private String nameProduct;
    private String quantityRequested;
    private BigDecimal priceProduct;
}
