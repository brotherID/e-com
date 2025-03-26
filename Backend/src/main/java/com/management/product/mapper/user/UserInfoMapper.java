package com.management.product.mapper.user;

import com.management.product.dtos.user.UserInfoRequest;
import com.management.product.dtos.user.UserInfoResponse;
import com.management.product.entities.user.UserInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "address", target = "address")
    UserInfo toUserInfo(UserInfoRequest userInfoRequest);
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "address", target = "address")
    UserInfoResponse toUserInfoResponse(UserInfo userInfo);

    List<UserInfoResponse> toListUserInfoResponse(List<UserInfo> userInfos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget UserInfo userInfo, UserInfoRequest userInfoRequest);
}
