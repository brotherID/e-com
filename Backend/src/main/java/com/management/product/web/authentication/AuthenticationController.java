package com.management.product.web.authentication;

import com.management.product.dtos.authentication.AuthenticationRequest;
import com.management.product.dtos.authentication.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationController {
    String URI_TOKEN = "/token";
    @PostMapping(value = URI_TOKEN)
    ResponseEntity<AuthenticationResponse> getToken(@RequestBody AuthenticationRequest authenticationRequest);
}
