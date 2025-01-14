package com.management.product.web.user;

import com.management.product.dtos.user.UserInfoRequest;
import com.management.product.dtos.user.UserInfoResponse;
import com.management.product.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserInfoService userInfoService;
    @Override
    public ResponseEntity<UserInfoResponse> addUser(UserInfoRequest userInfoRequest) {
        return ResponseEntity.ok(userInfoService.addUser(userInfoRequest));
    }

    @Override
    public ResponseEntity<List<UserInfoResponse>> getAllUser() {
        return ResponseEntity.ok(userInfoService.listUser());
    }
}
