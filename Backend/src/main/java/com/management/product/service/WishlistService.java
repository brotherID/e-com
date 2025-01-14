package com.management.product.service;

import com.management.product.dtos.wish.WishListProductResponse;
import com.management.product.dtos.product.ProductResponse;

import java.util.List;

public interface WishlistService {
    WishListProductResponse addProductToWishlist(Long idProduct);
    List<ProductResponse> getProductsOfWishlistUser();
}
