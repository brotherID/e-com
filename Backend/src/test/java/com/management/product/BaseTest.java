package com.management.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.product.dtos.authentication.AuthenticationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public abstract class  BaseTest {

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setUp() throws  Exception{
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("admin@admin.com");
        authenticationRequest.setPassword("testPassword");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(authenticationRequest);
        MvcResult result = mockMvc.perform(post("/api/v1/users/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertTrue(status == HttpStatus.OK.value() || status == HttpStatus.CONFLICT.value(), "Error catched ");
    }
}
