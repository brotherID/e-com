package com.management.product.dtos.authentication;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
}
