package com.management.product.service;


import com.management.product.dtos.cart.CartResponse;
import com.management.product.dtos.cart.AddProductToCartResponse;
import com.management.product.entities.cart.Cart;
import com.management.product.entities.product.ShoppedProduct;
import com.management.product.entities.promotion.Promotion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartService {
    AddProductToCartResponse addProductToCart(Long idProduct, Integer quantityRequested);

    BigDecimal calculateTotalAmountProductsWithPromotion(List<ShoppedProduct> shoppedProducts, Optional<Promotion> promotion);

    CartResponse getProductsOfCartUser();

    Cart save(Cart cart);

    Cart getCartOfAuthenticatedUser();
}
