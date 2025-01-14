package com.management.product.dtos.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserInfoRequest {
    private String username;
    private String firstname;
    private String email;
    private String password;
}
