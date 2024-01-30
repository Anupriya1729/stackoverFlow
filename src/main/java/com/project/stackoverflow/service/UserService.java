package com.project.stackoverflow.service;

import com.project.stackoverflow.dto.UserDTO;
import com.project.stackoverflow.entity.UserInfo;

import java.util.Optional;

public interface UserService {
    UserInfo registerUser(UserDTO userDTO);
    Optional<UserInfo> getUserDetails(Long userId);
}
