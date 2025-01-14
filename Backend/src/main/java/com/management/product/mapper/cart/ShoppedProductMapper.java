package com.management.product.mapper.cart;

import com.management.product.dtos.cart.AddProductToCartResponse;
import com.management.product.entities.product.ShoppedProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShoppedProductMapper {
    @Mapping(source = "idShoppedProduct", target = "idShoppedProduct")
    @Mapping(source = "idCart", target = "idCart")
    @Mapping(source = "idProduct", target = "idProduct")
    @Mapping(source = "quantityRequested", target = "quantityRequested")
    AddProductToCartResponse toShoppedProductResponse(ShoppedProduct shoppedProduct);
}
