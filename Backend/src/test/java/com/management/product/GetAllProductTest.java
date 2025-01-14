package com.management.product;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Slf4j
public class GetAllProductTest extends BaseTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthUtils authUtils;


    @Test
    public void testAddProduct() throws Exception {
        authUtils.addProduct();
    }

    @Test
    public void testGetAllProduct() throws Exception {
        String token = authUtils.obtainAccessToken("admin@admin.com", "testPassword");
        log.info("testGetAllProduct token : {}", token);
        MockHttpServletResponse
                mockHttpServletResponse =   mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        )
                .andReturn().getResponse();
        log.info("testGetAllProduct response  {}",mockHttpServletResponse.getContentAsString());

    }

}
