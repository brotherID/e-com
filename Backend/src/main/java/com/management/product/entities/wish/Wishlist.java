package com.management.product.entities.wish;

import com.management.product.entities.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String   idWishlist;
    @Column(unique = true)
    private String email;
}
