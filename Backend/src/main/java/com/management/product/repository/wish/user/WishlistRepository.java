package com.management.product.repository.wish.user;

import com.management.product.entities.wish.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist,String> {
    Optional<Wishlist> findByEmail(String email);
}
