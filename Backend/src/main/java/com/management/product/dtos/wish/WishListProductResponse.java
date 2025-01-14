package com.management.product.dtos.wish;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class WishListProductResponse {
    private String idWishListProduct;
    private String idWishlist;
    private Long idProduct;
}
