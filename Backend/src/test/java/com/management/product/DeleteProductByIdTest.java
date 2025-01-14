package com.management.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.product.dtos.product.ProductDetailResponse;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Slf4j
public class DeleteProductByIdTest extends BaseTest{


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private AuthUtils authUtils;


    @Test
    public void testAddProduct() throws Exception {
        authUtils.addProduct();
    }

    @Test
    public void deleteProductById() throws Exception {
        log.info("Begin deleteProductById ......  ");
        MvcResult resultAddProduct =  authUtils.addProduct();
        ObjectMapper objectMapper = new ObjectMapper();
        String responseContent = resultAddProduct.getResponse().getContentAsString();
        log.info("add product response : "+responseContent);
        ProductDetailResponse productDetailResponse = objectMapper.readValue(responseContent, ProductDetailResponse.class);
        Long idProduct = productDetailResponse.getIdProduct();
        log.info("Product created id : "+idProduct);
        String token = authUtils.obtainAccessToken("admin@admin.com", "testPassword");
        log.info("updateProductById token : {}", token);
        mockMvc.perform(delete("/api/v1/products/{idProduct}",idProduct)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)).andExpect(status().isNoContent()).andDo(print());
        log.info("End deleteProductById ......  ");

    }







}
