package com.management.product.entities.wish;

import com.management.product.entities.base.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WishListProduct extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idWishListProduct;
    private String idWishlist;
    private Long idProduct;
}
