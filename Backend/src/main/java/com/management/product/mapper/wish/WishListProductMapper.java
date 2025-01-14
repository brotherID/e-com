package com.management.product.mapper.wish;

import com.management.product.dtos.wish.WishListProductResponse;
import com.management.product.entities.wish.WishListProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishListProductMapper {
    @Mapping(source = "idWishListProduct", target = "idWishListProduct")
    @Mapping(source = "idWishlist", target = "idWishlist")
    @Mapping(source = "idProduct", target = "idProduct")
    WishListProductResponse toWishListProductResponse(WishListProduct wishListProduct);
    @Mapping(source = "idWishListProduct", target = "idWishListProduct")
    @Mapping(source = "idWishlist", target = "idWishlist")
    @Mapping(source = "idProduct", target = "idProduct")
    WishListProduct toWishListProduct(WishListProductResponse wishListProductResponse);
}
