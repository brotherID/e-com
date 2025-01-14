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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Slf4j
public class GetProductByIdTest extends BaseTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthUtils authUtils;


    @Test
    public void testGetProductById() throws Exception {

        MvcResult result =  authUtils.addProduct();

        ObjectMapper objectMapper = new ObjectMapper();
        String responseContent = result.getResponse().getContentAsString();
        ProductDetailResponse productDetailResponse = objectMapper.readValue(responseContent, ProductDetailResponse.class);
        Long idProduct = productDetailResponse.getIdProduct();
        log.info("Product created id : "+idProduct);

        String token = authUtils.obtainAccessToken("admin@admin.com", "testPassword");

        MockHttpServletResponse
                mockHttpServletResponse =   mockMvc.perform(get("/api/v1/products/{idProduct}",idProduct)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                )
                .andReturn().getResponse();
        log.info("testGetProductById response  {}",mockHttpServletResponse.getContentAsString());

    }

}
