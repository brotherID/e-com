package com.management.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.product.dtos.authentication.AuthenticationRequest;
import com.management.product.dtos.authentication.AuthenticationResponse;
import com.management.product.dtos.product.ProductRequest;
import com.management.product.enums.InventoryStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Service
public class AuthUtils {

    @Autowired
    private MockMvc mockMvc;

    public String obtainAccessToken(String email,String password) throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(email);
        authenticationRequest.setPassword(password);
        ObjectMapper objectMapper = new ObjectMapper();
        String authJson = objectMapper.writeValueAsString(authenticationRequest);
        log.info("authJson : {}",authJson);
        MvcResult result = mockMvc.perform(post("/api/authentication/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authJson))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        log.info("response : {}",response);
        AuthenticationResponse authenticationResponse = objectMapper.readValue(response,AuthenticationResponse.class);
        return authenticationResponse.getToken();

    }


    public MvcResult addProduct() throws Exception {
        String token = obtainAccessToken("admin@admin.com", "testPassword");
        ProductRequest productRequest = getProductRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(productRequest);
        log.info("productJson : {} ",productJson);
        return mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andReturn();
    }

    private static ProductRequest getProductRequest() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setCodeProduct("CodeProductTest1");
        productRequest.setNameProduct("nameProductTest1");
        productRequest.setDescriptionProduct("DescriptionProductTest1");
        productRequest.setImageProduct("ImageProductTest1");
        productRequest.setCategoryProduct("CategoryProductTest1");
        productRequest.setPriceProduct(new BigDecimal("123.45"));
        productRequest.setQuantityProduct(19);
        productRequest.setInternalReferenceProduct("InternalReferenceProductTest1");
        productRequest.setShellIdProduct(4444);
        productRequest.setInventoryStatus(InventoryStatus.INSTOCK);
        productRequest.setRatingProduct(222);
        return productRequest;
    }
}
