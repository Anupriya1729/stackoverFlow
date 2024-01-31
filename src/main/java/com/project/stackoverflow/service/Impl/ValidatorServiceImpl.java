package com.project.stackoverflow.service.Impl;

import com.project.stackoverflow.repository.UserInfoRepository;
import com.project.stackoverflow.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatorServiceImpl implements ValidatorService {
    @Autowired
    private UserInfoRepository userRepository;

    public boolean isUserInfoUnique(String email, String phone, String username) {
        return userRepository.findByUsername(username).isEmpty() &&
                userRepository.findByEmailAndPhone(email, phone).isEmpty();
    }
}
