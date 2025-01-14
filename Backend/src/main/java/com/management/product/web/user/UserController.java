package com.management.product.web.user;

import com.management.product.dtos.user.UserInfoRequest;
import com.management.product.dtos.user.UserInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserController {
    String URI_USER = "/user";
    @PostMapping(value = URI_USER)
    ResponseEntity<UserInfoResponse> addUser(@RequestBody UserInfoRequest userInfoRequest) ;
    @GetMapping
    ResponseEntity<List<UserInfoResponse>> getAllUser();
}
