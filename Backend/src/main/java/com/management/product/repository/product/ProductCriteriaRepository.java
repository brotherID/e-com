package com.management.product.repository.product;

import com.management.product.entities.product.Product;
import com.management.product.enums.InventoryStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class ProductCriteriaRepository {

    public static Specification<Product> filterByCriteria(String nameProduct, InventoryStatus inventoryStatus) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (nameProduct != null && !nameProduct.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("nameProduct")), "%" + nameProduct.toLowerCase() + "%"));
            }
            if (inventoryStatus != null) {
                predicate = cb.and(predicate, cb.equal(root.get("inventoryStatus"), inventoryStatus));
            }
            return predicate;
        };
    }

}
