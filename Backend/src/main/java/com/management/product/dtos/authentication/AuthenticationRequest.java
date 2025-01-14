package com.management.product.dtos.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
}
