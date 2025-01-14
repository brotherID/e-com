package com.management.product.service;

import com.management.product.dtos.user.UserInfoRequest;
import com.management.product.dtos.user.UserInfoResponse;
import com.management.product.entities.user.UserInfo;
import com.management.product.mapper.user.UserInfoMapper;
import com.management.product.repository.user.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;

import java.util.List;

import static org.zalando.problem.Status.CONFLICT;

@RequiredArgsConstructor
@Service
public class UserInfoServiceImpl implements UserInfoService {

    public static final String USER_FOUNDED = "User founded";
    public static final String USER_EXIST = "The user with email %s  exist.";
    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserInfoResponse addUser(UserInfoRequest userInfoRequest) {
        userInfoRepository.findByEmail(userInfoRequest.getEmail())
                .ifPresent(userInfoFound -> {
                    throw Problem.builder()
                            .withTitle(USER_FOUNDED)
                            .withStatus(CONFLICT)
                            .withDetail(String.format(USER_EXIST, userInfoFound.getEmail()))
                            .build();
                });
        userInfoRequest.setPassword(passwordEncoder.encode(userInfoRequest.getPassword()));
        UserInfo userInfo = userInfoRepository.save(userInfoMapper.toUserInfo(userInfoRequest));
        return userInfoMapper.toUserInfoResponse(userInfo);
    }

    @Override
    public List<UserInfoResponse> listUser() {
        return userInfoMapper.toListUserInfoResponse(userInfoRepository.findAll());
    }
}
