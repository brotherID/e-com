package com.management.product.web.graphql.user;

import com.management.product.dtos.user.UserInfoRequest;
import com.management.product.entities.user.UserInfo;
import com.management.product.mapper.user.UserInfoMapper;
import com.management.product.repository.user.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;
    private final PasswordEncoder passwordEncoder;

    @QueryMapping
    List<UserInfo> users()
    {
        return userInfoRepository.findAll();
    }

    @QueryMapping
    Optional<UserInfo> userById(@Argument Long id)
    {
        return userInfoRepository.findById(id);
    }

    @MutationMapping
    UserInfo addUser(@Argument UserInfoRequest user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userInfoRepository.save(userInfoMapper.toUserInfo(user));
    }



}
