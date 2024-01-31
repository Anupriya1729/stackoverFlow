package com.project.stackoverflow.repository;

import com.project.stackoverflow.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);
    Optional<UserInfo> findByEmailAndPhone(String email, String phone);
}