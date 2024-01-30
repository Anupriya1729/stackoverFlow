package com.project.stackoverflow.controller;

import com.project.stackoverflow.dto.UserDTO;
import com.project.stackoverflow.entity.UserInfo;
import com.project.stackoverflow.service.QuestionService;
import com.project.stackoverflow.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/register")
    public ResponseEntity<UserInfo> createAccount(@RequestBody UserDTO createUserRequest) {
        try {
            UserInfo user = userService.registerUser(createUserRequest);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable @NotNull Long userId){
        Optional<UserInfo> user = userService.getUserDetails(userId);
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }
}
