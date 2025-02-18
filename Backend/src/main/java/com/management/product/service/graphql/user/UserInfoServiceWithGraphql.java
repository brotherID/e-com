package com.management.product.service.graphql.user;

import com.management.product.dtos.user.UserInfoRequest;
import com.management.product.dtos.user.UserInfoResponse;

import java.util.List;
import java.util.Optional;

public interface UserInfoServiceWithGraphql {
    UserInfoResponse addUser(UserInfoRequest userInfoRequest);
    List<UserInfoResponse> listUser();
    Optional<UserInfoResponse> findUserById(Long id);
    void deleteUserById(Long id);
    UserInfoResponse updateUserById(Long id,UserInfoRequest userInfoRequest);
}
