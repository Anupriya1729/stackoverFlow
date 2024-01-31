package com.project.stackoverflow.service;

import com.project.stackoverflow.dto.UserDTO;
import com.project.stackoverflow.entity.UserInfo;
import com.project.stackoverflow.exception.UserAlreadyExistsException;

import java.util.Optional;

public interface UserService {
    UserInfo registerUser(UserDTO userDTO) throws UserAlreadyExistsException;
    Optional<UserInfo> getUserDetails(Long userId);
}
