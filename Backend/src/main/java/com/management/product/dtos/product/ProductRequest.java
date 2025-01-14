package com.management.product.dtos.product;

import com.management.product.enums.InventoryStatus;
import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String   codeProduct;
    private String   nameProduct;
    private String   descriptionProduct;
    private String   imageProduct;
    private String   categoryProduct;
    private BigDecimal priceProduct;
    private Integer  quantityProduct;
    private String   internalReferenceProduct;
    private Integer  shellIdProduct;
    private InventoryStatus inventoryStatus;
    private Integer  ratingProduct;
    private Boolean applicableDiscount;
}
