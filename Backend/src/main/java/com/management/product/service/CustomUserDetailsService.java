package com.management.product.service;

import com.management.product.entities.user.UserInfo;
import com.management.product.repository.user.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;


@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    public static final String USER_NOT_EXISTS_BY_USERNAME_OR_EMAIL = "User not exists by Username or Email";
    private final UserInfoRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByEmail(email) .orElseThrow(() ->
                new UsernameNotFoundException(USER_NOT_EXISTS_BY_USERNAME_OR_EMAIL));
        return new User(
                email,
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
