package com.project.stackoverflow.service;

public interface ValidatorService {
    boolean isUserInfoUnique(String email, String phone, String username);
}
