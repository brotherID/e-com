package com.management.product.service;

import com.management.product.dtos.cart.CartResponse;
import com.management.product.dtos.cart.AddProductToCartResponse;
import com.management.product.entities.cart.Cart;
import com.management.product.entities.product.Product;
import com.management.product.entities.product.ShoppedProduct;
import com.management.product.entities.promotion.Promotion;
import com.management.product.enums.InventoryStatus;
import com.management.product.mapper.product.ProductMapper;
import com.management.product.mapper.cart.ShoppedProductMapper;
import com.management.product.repository.cart.user.CartRepository;
import com.management.product.repository.product.ProductRepository;
import com.management.product.repository.cart.product.ShoppedProductRepository;
import com.management.product.repository.promotion.PromotionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.management.product.web.product.ProductControllerImpl.PRODUCT_NOT_FOUND;
import static org.zalando.problem.Status.CONFLICT;
import static org.zalando.problem.Status.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    public static final String MESSAGE_DETAIL_PRODUCT_ID_NOT_EXIST = "Product with idProduct %s does not exist !";
    public static final String TITLE_LOW_QUANTITY = "low quantity";
    public static final String DETAIL_QUANTITY_LESS_QUANTITY_REQUESTED = "the quantity available %d is less than the quantity requested %d";
    private final CartRepository cartRepository;
    private final ShoppedProductRepository shoppedProductRepository;
    private final ProductRepository productRepository;
    private final ShoppedProductMapper shoppedProductMapper;
    private final ProductMapper productMapper;
    @Value("${product.inventory.low.stock}")
    private Integer productInventoryNumber;
    @Value("${cart.expiration.date}")
    private long cartExpirationDate;
    private final PromotionRepository promotionRepository;


    @Override
    public AddProductToCartResponse addProductToCart(Long idProduct, Integer quantityRequested) {
        log.info("addProductToCart()[idProduct :{}] ...", idProduct);
        Product productToAdd = productRepository.findById(idProduct)
                .orElseThrow(() -> Problem.builder()
                        .withTitle(PRODUCT_NOT_FOUND)
                        .withStatus(NOT_FOUND)
                        .withDetail(String.format(MESSAGE_DETAIL_PRODUCT_ID_NOT_EXIST, idProduct))
                        .build());
        if (productToAdd.getQuantityProduct() >= quantityRequested) {
            Integer quantityProductToUpdate = productToAdd.getQuantityProduct() - quantityRequested;
            productToAdd.setQuantityProduct(quantityProductToUpdate);
            productToAdd.setInventoryStatus(calculateInventory(quantityProductToUpdate));
            productRepository.save(productToAdd);
        } else {
            throw Problem.builder()
                    .withTitle(TITLE_LOW_QUANTITY)
                    .withStatus(CONFLICT)
                    .withDetail(String.format(DETAIL_QUANTITY_LESS_QUANTITY_REQUESTED, productToAdd.getQuantityProduct(), quantityRequested))
                    .build();
        }
        Cart cartOfAuthenticatedUser = getCartOfAuthenticatedUser();
        AddProductToCartResponse addProductToCartResponse = addProductToCard(cartOfAuthenticatedUser.getIdCart(), productToAdd.getIdProduct(), quantityRequested);
        addProductToCartResponse.setTotalShoppedProduct(getProductsOfCartUser().getTotalShoppedProduct());
        log.info("addProductToCart()[idProduct :{}] Done", idProduct);
        return addProductToCartResponse;
    }


    private BigDecimal calculateTotalAmountProducts(List<ShoppedProduct> shoppedProducts) {
        return shoppedProducts.stream()
                .map(shoppedProduct -> {
                    BigDecimal amount = BigDecimal.ZERO;
                    Optional<Product> productOptional = productRepository.findById(shoppedProduct.getIdProduct());
                    if (productOptional.isPresent()) {
                        Product product = productOptional.get();
                        amount = product.getPriceProduct().multiply(BigDecimal.valueOf(shoppedProduct.getQuantityRequested()));
                    }
                    return amount;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateTotalAmountProductsWithPromotion(List<ShoppedProduct> shoppedProducts, Optional<Promotion> promotionOptional) {
        return shoppedProducts.stream()
                .map(shoppedProduct -> {
                    BigDecimal amount = BigDecimal.ZERO;
                    Optional<Product> productOptional = productRepository.findById(shoppedProduct.getIdProduct());
                    if (productOptional.isPresent()) {
                        Product product = productOptional.get();
                        amount = product.getPriceProduct().multiply(BigDecimal.valueOf(shoppedProduct.getQuantityRequested()));
                        if (Boolean.TRUE.equals(product.getApplicableDiscount()) && promotionOptional.isPresent()) {
                            amount = amount.subtract(amount.multiply(BigDecimal.valueOf(promotionOptional.get().getDiscountPercentage())).divide(new BigDecimal(100), RoundingMode.CEILING));
                        }
                    }
                    return amount;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public InventoryStatus calculateInventory(Integer quantityProductToUpdate) {
        if (quantityProductToUpdate == 0) {
            return InventoryStatus.OUTOFSTOCK;
        } else if (quantityProductToUpdate > productInventoryNumber) {
            return InventoryStatus.INSTOCK;
        } else {
            return InventoryStatus.LOWSTOCK;
        }
    }

    @Override
    public CartResponse getProductsOfCartUser() {
        log.info("getProductsOfCartUser() ...");
        CartResponse cartResponse = new CartResponse();
        Cart cart = getCartOfAuthenticatedUser();
        List<ShoppedProduct> shoppedProduct = getShoppedProductsFromCartUser(cart);
        cartResponse.setShoppedProducts(shoppedProduct.stream()
                .map(productMapper::toShoppedProductResponse)
                .peek(productResponse -> productRepository.findById(productResponse.getIdProduct()).ifPresent(product -> {
                    productResponse.setCodeProduct(product.getCodeProduct());
                    productResponse.setPriceProduct(product.getPriceProduct());
                    productResponse.setNameProduct(product.getNameProduct());
                        })
                )
                .toList());
        Optional<Promotion> promotion = Optional.empty();
        if (cart.getCodePromotion() != null) {
            promotion = promotionRepository.findByCodePromotion(cart.getCodePromotion());
        }

        BigDecimal totalAmount = calculateTotalAmountProductsWithPromotion(shoppedProduct, promotion);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
        cartResponse.setTotalAmount(totalAmount);
        cartResponse.setTotalShoppedProduct(shoppedProduct.stream().map(ShoppedProduct::getQuantityRequested).reduce(0, Integer::sum));
        log.info("getProductsOfCartUser() Done");
        return cartResponse;
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }


    private List<ShoppedProduct> getShoppedProductsFromCartUser(Cart cart) {
        return Optional.of(cart)
                .map(Cart::getIdCart)
                .map(shoppedProductRepository::findByIdCart)
                .orElse(List.of());
    }


    @Override
    public Cart getCartOfAuthenticatedUser() {
        Authentication currentAuthenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        return cartRepository.findByEmail(currentAuthenticatedUser.getName())
                .map(cart -> {
                    if (cart.getExpirationDate().isBefore(LocalDateTime.now())) {
                        cleanUserCart(cart);
                        return null;
                    }
                    return cart;
                })
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setEmail(currentAuthenticatedUser.getName());
                    newCart.setExpirationDate(LocalDateTime.now().plusMinutes(cartExpirationDate));
                    newCart.setTotalAmount(BigDecimal.ZERO);
                    return cartRepository.save(newCart);
                });
    }

    private void cleanUserCart(Cart cart) {
        cartRepository.delete(cart);
        shoppedProductRepository.findByIdCart(cart.getIdCart())
                .forEach(shoppedProductRepository::delete);


    }


    public AddProductToCartResponse addProductToCard(Long idCart, Long idProduct, Integer quantityRequested) {
        ShoppedProduct shoppedProduct = ShoppedProduct.builder()
                .idCart(idCart)
                .idProduct(idProduct)
                .quantityRequested(quantityRequested)
                .build();
        return shoppedProductMapper.toShoppedProductResponse(shoppedProductRepository.save(shoppedProduct));
    }
}
