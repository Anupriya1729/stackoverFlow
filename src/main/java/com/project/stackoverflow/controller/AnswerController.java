package com.project.stackoverflow.controller;

import com.project.stackoverflow.constant.PostType;
import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.exception.UserNotFoundException;
import com.project.stackoverflow.service.PostService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    PostService postService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDTO>> getAllAnswersForUser(@PathVariable @NotNull Long userId) throws UserNotFoundException {
        List<PostDTO> updatedAnswers = postService.getAllPostsForUser(userId, PostType.ANSWER);
        return ResponseEntity.ok(updatedAnswers);
    }
}
