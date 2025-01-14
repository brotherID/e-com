package com.management.product.service;

import com.management.product.dtos.product.ProductDetailResponse;
import com.management.product.dtos.product.ProductRequest;
import com.management.product.dtos.product.ProductResponse;
import com.management.product.enums.InventoryStatus;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {
    ProductDetailResponse createProduct(ProductRequest productRequest);
    Optional<ProductDetailResponse> getProductById(Long idProduct);
    ProductDetailResponse updateProductById(Long idProduct,ProductRequest productRequest);
    void  removeProductById(Long idProduct);
    boolean isAdmin();
    Page<ProductResponse> getProducts(String nameProduct, int page, int size, InventoryStatus inventoryStatus, boolean sortDesc);
}
