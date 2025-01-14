package com.management.product.web.wish;

import com.management.product.dtos.wish.WishListProductResponse;
import com.management.product.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/wishList")
@RequiredArgsConstructor
public class WishListControllerImpl implements WishListController {
    private final WishlistService wishlistService;
    @Override
    public ResponseEntity<WishListProductResponse> addProductToWishList(Long idProduct) {
        WishListProductResponse wishListProductResponse =  wishlistService.addProductToWishlist(idProduct);
        return ResponseEntity
                .ok(wishListProductResponse);
    }
}
