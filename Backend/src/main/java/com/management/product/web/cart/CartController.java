package com.management.product.web.cart;


import com.management.product.dtos.cart.AddProductToCartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface CartController {
    String URI_ID_PRODUCT = "/{idProduct}";
    String URI_QUANTITY_REQUESTED = "/{quantityRequested}";
    String PATH_VARIABLE_ID_PRODUCT = "idProduct";
    String PATH_VARIABLE_QUANTITY_REQUESTED = "quantityRequested";
    @PostMapping(value =URI_ID_PRODUCT+"/quantityRequested"+URI_QUANTITY_REQUESTED+"/cart" )
    ResponseEntity<AddProductToCartResponse> addProductToCart(@PathVariable(name = PATH_VARIABLE_ID_PRODUCT) Long idProduct, @PathVariable(name= PATH_VARIABLE_QUANTITY_REQUESTED) Integer quantityRequested);
}

