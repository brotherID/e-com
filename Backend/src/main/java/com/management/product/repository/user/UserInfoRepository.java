package com.management.product.repository.user;

import com.management.product.entities.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    Optional<UserInfo> findByEmail(String email);
}
