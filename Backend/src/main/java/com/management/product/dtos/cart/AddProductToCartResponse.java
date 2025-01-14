package com.management.product.dtos.cart;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AddProductToCartResponse {
    private Long idShoppedProduct;
    private Long idCart;
    private Long idProduct;
    private Integer quantityRequested;
}
