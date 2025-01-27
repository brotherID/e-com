package com.management.product.service;

import com.management.product.dtos.product.ProductDetailResponse;
import com.management.product.dtos.product.ProductRequest;
import com.management.product.dtos.product.ProductResponse;
import com.management.product.entities.product.Product;
import com.management.product.enums.InventoryStatus;
import com.management.product.mapper.product.ProductMapper;
import com.management.product.repository.product.ProductCriteriaRepository;
import com.management.product.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;

import java.util.Optional;

import static org.zalando.problem.Status.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements  ProductService{

    public static final String PRODUCT_NOT_FOUNDED = "product  not founded";
    public static final String DETAIL_PRODUCT_NOT_FOUNDED = "the product with id  %s not founded";
    private final ProductMapper productMapper;
    private  final ProductRepository productRepository;

   
    @Override
    public ProductDetailResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        return productMapper.fromEntity(productRepository.save(product));
    }


    @Override
    public Optional<ProductDetailResponse> getProductById(Long idProduct) {
        log.info("Begin getProductById , idProduct :  {}",idProduct);
        return productRepository.findById(idProduct)
                .map(productMapper::fromEntity);
    }



    @Override
    public ProductDetailResponse updateProductById(Long idProduct, ProductRequest productRequest) {
        log.info("Begin updateProductById , idProduct :  {} , [{}]",idProduct,productRequest.toString());
        Product product =  productRepository.findById(idProduct)
                .orElseThrow(()-> Problem.builder()
                        .withTitle(PRODUCT_NOT_FOUNDED)
                        .withStatus(NOT_FOUND)
                        .withDetail(String.format(DETAIL_PRODUCT_NOT_FOUNDED, idProduct))
                        .build());
        productMapper.updateEntity(product, productRequest);
        return productMapper.fromEntity(productRepository.save(product));
    }

    @Override
    public void  removeProductById(Long idProduct) {
        Product product =  productRepository.findById(idProduct)
                .orElseThrow(
                        ()-> Problem.builder()
                        .withTitle(PRODUCT_NOT_FOUNDED)
                        .withStatus(NOT_FOUND)
                        .withDetail(String.format(DETAIL_PRODUCT_NOT_FOUNDED, idProduct))
                        .build()
                );
        productRepository.deleteById(idProduct);
    }

    @Override
    public boolean isAdmin() {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        return "admin@admin.com".equals(currentAuth.getName());
    }


    @Override
    public Page<ProductResponse> getProducts(String nameProduct,int page, int size, InventoryStatus inventoryStatus, boolean sortDesc) {
        Sort sort = sortDesc ? Sort.by("createdAt").descending() : Sort.by("createdAt").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return productMapper.toDtoPage(productRepository.findAll(
                ProductCriteriaRepository.filterByCriteria(nameProduct, inventoryStatus),
                pageable
        ));
    }
}
