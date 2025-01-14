package com.management.product.dtos.cart;

import com.management.product.dtos.product.ShoppedProductResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private List<ShoppedProductResponse> shoppedProducts;
    private BigDecimal totalAmount;
    private Integer totalShoppedProduct;
}
