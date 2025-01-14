package com.management.product.entities.product;


import com.management.product.entities.base.Auditable;
import com.management.product.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;
    private String codeProduct;
    private String nameProduct;
    private String descriptionProduct;
    private String imageProduct;
    private String categoryProduct;
    private BigDecimal priceProduct;
    private Integer quantityProduct;
    private String internalReferenceProduct;
    private Integer shellIdProduct;
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;
    private Integer ratingProduct;
    private Boolean applicableDiscount;
}
