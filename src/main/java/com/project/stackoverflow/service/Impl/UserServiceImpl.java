package com.project.stackoverflow.service.Impl;

import com.project.stackoverflow.constant.UserStatus;
import com.project.stackoverflow.dto.UserDTO;
import com.project.stackoverflow.entity.UserInfo;
import com.project.stackoverflow.exception.UserAlreadyExistsException;
import com.project.stackoverflow.repository.UserInfoRepository;
import com.project.stackoverflow.service.UserService;
import com.project.stackoverflow.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userRepository;

    @Autowired
    private ValidatorService validatorService;

    public UserInfo registerUser(UserDTO createUserRequest) throws UserAlreadyExistsException {
        if(!validatorService.isUserInfoUnique(createUserRequest.getEmail(), createUserRequest.getPhone(), createUserRequest.getUsername())){
            throw new UserAlreadyExistsException("User Already exists.");
        }

        UserInfo user = new UserInfo();
        user.setStatus(UserStatus.ACTIVE);
        user.setName(createUserRequest.getName());
        user.setEmail(createUserRequest.getEmail());
        user.setPhone(createUserRequest.getPhone());
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(createUserRequest.getPassword());
        user.setReputationPoints(10);

        userRepository.save(user);

        return user;
    }

    public Optional<UserInfo> getUserDetails(Long userId) {
        return userRepository.findById(userId);
    }
}
