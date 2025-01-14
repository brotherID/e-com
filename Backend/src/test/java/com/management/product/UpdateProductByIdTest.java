package com.management.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.product.dtos.product.ProductDetailResponse;
import com.management.product.dtos.product.ProductRequest;
import com.management.product.enums.InventoryStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Slf4j
public class UpdateProductByIdTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthUtils authUtils;





    @Test
    public void updateProductById() throws Exception {

        MvcResult resultAddProduct =  authUtils.addProduct();


        ObjectMapper objectMapper = new ObjectMapper();
        String responseContent = resultAddProduct.getResponse().getContentAsString();
        log.info("add product response : "+responseContent);
        ProductDetailResponse productDetailResponse = objectMapper.readValue(responseContent, ProductDetailResponse.class);
        Long idProduct = productDetailResponse.getIdProduct();
        log.info("Product created id : "+idProduct);



        String token = authUtils.obtainAccessToken("admin@admin.com", "testPassword");
        log.info("updateProductById token : {}", token);
        String productUpdated = objectMapper.writeValueAsString(getProductRequest());
        MvcResult resultPatchProduct = mockMvc.perform(patch("/api/v1/products/{idProduct}",idProduct)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                .content(productUpdated))
                .andExpect(status().isOk()).andReturn();
        log.info("updateProductById response "+resultPatchProduct.getResponse().getContentAsString());

    }

    private static ProductRequest getProductRequest() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setCodeProduct("CodeProductTest2");
        productRequest.setNameProduct("nameProductTest2");
        productRequest.setDescriptionProduct("DescriptionProductTest2");
        productRequest.setImageProduct("ImageProductTest2");
        productRequest.setCategoryProduct("CategoryProductTest2");
        productRequest.setPriceProduct(new BigDecimal("123.45"));
        productRequest.setQuantityProduct(191);
        productRequest.setInternalReferenceProduct("InternalReferenceProductTest2");
        productRequest.setShellIdProduct(4441);
        productRequest.setInventoryStatus(InventoryStatus.INSTOCK);
        productRequest.setRatingProduct(221);
        return productRequest;
    }




}
