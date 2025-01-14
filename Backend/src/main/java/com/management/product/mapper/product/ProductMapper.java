package com.management.product.mapper.product;

import com.management.product.dtos.product.ProductDetailResponse;
import com.management.product.dtos.product.ProductRequest;
import com.management.product.dtos.product.ProductResponse;
import com.management.product.dtos.product.ShoppedProductResponse;
import com.management.product.entities.product.Product;
import com.management.product.entities.product.ShoppedProduct;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

     @Mapping(source = "idProduct", target = "idProduct")
     @Mapping(source = "codeProduct", target = "codeProduct")
     @Mapping(source = "nameProduct", target = "nameProduct")
     @Mapping(source = "descriptionProduct", target = "descriptionProduct")
     @Mapping(source = "imageProduct", target = "imageProduct")
     @Mapping(source = "categoryProduct", target = "categoryProduct")
     @Mapping(source = "priceProduct", target = "priceProduct")
     @Mapping(source = "quantityProduct", target = "quantityProduct")
     @Mapping(source = "internalReferenceProduct", target = "internalReferenceProduct")
     @Mapping(source = "shellIdProduct", target = "shellIdProduct")
     @Mapping(source = "inventoryStatus", target = "inventoryStatus")
     @Mapping(source = "ratingProduct", target = "ratingProduct")
     @Mapping(source = "applicableDiscount", target = "applicableDiscount")
     ProductDetailResponse fromEntity(Product product);

     @Mapping(source = "idProduct", target = "idProduct")
     @Mapping(source = "codeProduct", target = "codeProduct")
     @Mapping(source = "nameProduct", target = "nameProduct")
     @Mapping(source = "priceProduct", target = "priceProduct")
     @Mapping(source = "quantityProduct", target = "quantityProduct")
     ProductResponse toProductResponse(Product product);


     @Mapping(source = "idProduct", target = "idProduct")
     @Mapping(source = "quantityRequested", target = "quantityRequested")
     ShoppedProductResponse toShoppedProductResponse(ShoppedProduct shoppedProduct);

     @Mapping(source = "codeProduct", target = "codeProduct")
     @Mapping(source = "nameProduct", target = "nameProduct")
     @Mapping(source = "descriptionProduct", target = "descriptionProduct")
     @Mapping(source = "imageProduct", target = "imageProduct")
     @Mapping(source = "categoryProduct", target = "categoryProduct")
     @Mapping(source = "priceProduct", target = "priceProduct")
     @Mapping(source = "quantityProduct", target = "quantityProduct")
     @Mapping(source = "internalReferenceProduct", target = "internalReferenceProduct")
     @Mapping(source = "shellIdProduct", target = "shellIdProduct")
     @Mapping(source = "inventoryStatus", target = "inventoryStatus")
     @Mapping(source = "ratingProduct", target = "ratingProduct")
     @Mapping(source = "applicableDiscount", target = "applicableDiscount")
     Product toEntity(ProductRequest productRequest);

     List<Product> toProductList(List<ProductRequest> productRequests);

     List<ProductResponse> toProductDtoList(List<Product> products);

     default Page<ProductResponse> toDtoPage(Page<Product> products) {
          return products.map(this::toProductResponse);
     }

     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     void updateEntity(@MappingTarget Product product, ProductRequest productRequest);
}
