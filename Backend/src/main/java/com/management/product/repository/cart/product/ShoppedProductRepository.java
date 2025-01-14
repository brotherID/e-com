package com.management.product.repository.cart.product;

import com.management.product.entities.product.ShoppedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppedProductRepository extends JpaRepository<ShoppedProduct,Long> {
    List<ShoppedProduct> findByIdCart(Long idCart);
}
