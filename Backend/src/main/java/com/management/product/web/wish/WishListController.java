package com.management.product.web.wish;

import com.management.product.dtos.wish.WishListProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface WishListController {
    String URI_ID_PRODUCT = "/{idProduct}";
    String PATH_VARIABLE_ID_PRODUCT = "idProduct";
    String URI_PRODUCT = "/product";

    @PostMapping(value = URI_PRODUCT +URI_ID_PRODUCT)
    ResponseEntity<WishListProductResponse> addProductToWishList(@PathVariable(name = PATH_VARIABLE_ID_PRODUCT) Long idProduct);
}
