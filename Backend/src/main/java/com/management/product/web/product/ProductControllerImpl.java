package com.management.product.web.product;

import com.management.product.dtos.cart.CartResponse;
import com.management.product.dtos.product.ProductDetailResponse;
import com.management.product.dtos.product.ProductRequest;
import com.management.product.dtos.product.ProductResponse;
import com.management.product.enums.InventoryStatus;
import com.management.product.service.CartService;
import com.management.product.service.ProductService;
import com.management.product.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zalando.problem.Problem;

import java.net.URI;
import java.util.List;

import static org.zalando.problem.Status.*;


@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    public static final String PRODUCT_NOT_FOUND = "PRODUCT NOT FOUND";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized Access";
    public static final String THE_USER_IS_NOT_AUTHORIZED = "The user is not authorized.";
    public static final String THE_PRODUCT_NOT_FOUNDED = "The product with id %s not founded.";
    private final ProductService productService;
    private final CartService cartService;
    private final WishlistService wishlistService;
    @Override
    public ResponseEntity<ProductDetailResponse> addProduct(ProductRequest productRequest) {
            if(productService.isAdmin()){
                ProductDetailResponse product = productService.createProduct(productRequest);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(product.getIdProduct())
                        .toUri();
                return ResponseEntity
                        .created(location)
                        .body(product);
            }else{
                throw Problem.builder()
                        .withTitle(UNAUTHORIZED_ACCESS)
                        .withStatus(UNAUTHORIZED)
                        .withDetail(THE_USER_IS_NOT_AUTHORIZED)
                        .build();
            }
    }

    @Override
    public ResponseEntity<ProductDetailResponse> getProductById(Long idProduct) {
        return productService.getProductById(idProduct)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> Problem.builder()
                        .withTitle(PRODUCT_NOT_FOUND)
                        .withStatus(NOT_FOUND)
                        .withDetail(String.format(THE_PRODUCT_NOT_FOUNDED, idProduct))
                        .build());
    }


    @Override
    public ResponseEntity<ProductDetailResponse> updateProductById(Long idProduct, ProductRequest productRequest) {
            if(productService.isAdmin()){
                return ResponseEntity.ok(productService.updateProductById(idProduct, productRequest));
            }else{
                throw Problem.builder()
                        .withTitle(UNAUTHORIZED_ACCESS)
                        .withStatus(UNAUTHORIZED)
                        .withDetail(THE_USER_IS_NOT_AUTHORIZED)
                        .build();
            }
    }

    @Override
    public ResponseEntity<Void> deleteProductById(Long  idProduct) {
            if(productService.isAdmin()){
                productService.removeProductById(idProduct);
                return ResponseEntity.noContent().build();
            }else {
                throw Problem.builder()
                        .withTitle(UNAUTHORIZED_ACCESS)
                        .withStatus(UNAUTHORIZED)
                        .withDetail(THE_USER_IS_NOT_AUTHORIZED)
                        .build();
            }
    }

    @Override
    public ResponseEntity<CartResponse> getProductsOfCartUser() {
        return ResponseEntity.ok(cartService.getProductsOfCartUser());
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getProductsOfWishlistUser() {
        return ResponseEntity.ok(wishlistService.getProductsOfWishlistUser());
    }

    @Override
    public Page<ProductResponse> getProducts(String nameProduct, int page, int size, InventoryStatus inventoryStatus, boolean sortDesc) {
        return productService.getProducts(nameProduct,page, size,inventoryStatus,sortDesc);
    }

}
