package com.management.product.repository.wish.product;

import com.management.product.entities.wish.WishListProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListProductRepository extends JpaRepository<WishListProduct,String> {
    List<WishListProduct> findByIdWishlist(String idWishlist);
}