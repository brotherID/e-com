package com.management.product.web.product;

import com.management.product.dtos.cart.CartResponse;
import com.management.product.dtos.product.ProductDetailResponse;
import com.management.product.dtos.product.ProductRequest;
import com.management.product.dtos.product.ProductResponse;
import com.management.product.enums.InventoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface ProductController {
    String PATH_VARIABLE_ID_PRODUCT = "idProduct";
    String URI_ID_PRODUCT = "/{idProduct}";
    String URI_CART_USER = "/cart-user";
    String URI_WISH_LIST = "/wish-list";
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    ResponseEntity<ProductDetailResponse> addProduct(@RequestBody ProductRequest productRequest);

    @GetMapping(value = URI_ID_PRODUCT)
    ResponseEntity<ProductDetailResponse> getProductById(@PathVariable(name = PATH_VARIABLE_ID_PRODUCT) Long idProduct);

    @PatchMapping(value = URI_ID_PRODUCT)
    ResponseEntity<ProductDetailResponse> updateProductById(@PathVariable(name = PATH_VARIABLE_ID_PRODUCT) Long idProduct , @RequestBody ProductRequest productRequest);

    @DeleteMapping(value = URI_ID_PRODUCT)
    ResponseEntity<Void> deleteProductById(@PathVariable(name = PATH_VARIABLE_ID_PRODUCT) Long idProduct );

    @GetMapping(value = URI_CART_USER)
    ResponseEntity<CartResponse> getProductsOfCartUser();
    @GetMapping(value = URI_WISH_LIST)
    ResponseEntity<List<ProductResponse>> getProductsOfWishlistUser();
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    Page<ProductResponse> getProducts(
            @RequestParam(required = false) String nameProduct,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "inventory", required = false) InventoryStatus inventoryStatus,
            @RequestParam(name = "sortDesc", defaultValue = "true") boolean sortDesc) ;

}
