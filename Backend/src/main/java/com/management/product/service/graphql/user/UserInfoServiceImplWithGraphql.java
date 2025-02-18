package com.management.product.service.graphql.user;

import com.management.product.dtos.user.UserInfoRequest;
import com.management.product.dtos.user.UserInfoResponse;
import com.management.product.entities.user.UserInfo;
import com.management.product.mapper.user.UserInfoMapper;
import com.management.product.repository.user.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserInfoServiceImplWithGraphql implements  UserInfoServiceWithGraphql{

    public static final String USER_EXIST = "The user with email %s  exist.";
    public static final String USER_NOT_EXIST = "user with %d does not exist ";
    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserInfoResponse addUser(UserInfoRequest userInfoRequest) {
        userInfoRepository.findByEmail(userInfoRequest.getEmail())
                .ifPresent(userInfoFound -> {
                    throw new IllegalArgumentException(
                            String.format(USER_EXIST, userInfoFound.getEmail())
                    );
                });
        userInfoRequest.setPassword(passwordEncoder.encode(userInfoRequest.getPassword()));
        UserInfo userInfo = userInfoRepository.save(userInfoMapper.toUserInfo(userInfoRequest));
        return userInfoMapper.toUserInfoResponse(userInfo);
    }

    @Override
    public List<UserInfoResponse> listUser() {
        return userInfoMapper.toListUserInfoResponse(userInfoRepository.findAll());
    }

    @Override
    public Optional<UserInfoResponse> findUserById(Long id) {
         return userInfoRepository.findById(id).
                map(userInfoMapper::toUserInfoResponse);
    }

    @Override
    public void deleteUserById(Long id) {
        UserInfo userInfo =  userInfoRepository.findById(id).
                orElseThrow(() ->  new IllegalArgumentException(String.format(USER_NOT_EXIST, id)));
        userInfoRepository.deleteById(id);
    }

    @Override
    public UserInfoResponse updateUserById(Long id, UserInfoRequest userInfoRequest) {
        UserInfo userInfo =  userInfoRepository.findById(id).
                orElseThrow(() ->  new IllegalArgumentException(String.format(USER_NOT_EXIST, id)));
        userInfoMapper.updateEntity(userInfo,userInfoRequest);
        return userInfoMapper.toUserInfoResponse(userInfoRepository.save(userInfo));
    }
}
