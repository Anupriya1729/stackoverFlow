package com.project.stackoverflow.controller;

import com.project.stackoverflow.constant.PostType;
import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.dto.QuestionsDTO;
import com.project.stackoverflow.exception.MultipleTagsFoundException;
import com.project.stackoverflow.exception.PostNotFoundException;
import com.project.stackoverflow.exception.UserNotFoundException;
import com.project.stackoverflow.service.PostService;
import com.project.stackoverflow.service.QuestionService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PostService postService;

    @PostMapping("/{questionId}/add-tag")
    public ResponseEntity<PostDTO> addTagToQuestion (
            @PathVariable Long questionId,
            @RequestBody Set<String> addTagRequest) throws PostNotFoundException, MultipleTagsFoundException{
        PostDTO updatedQuestion = questionService.addTagsToQuestion(questionId, addTagRequest, false, null);
        return ResponseEntity.ok(updatedQuestion);
    }

    @GetMapping("/top-questions")
    public ResponseEntity<Page<QuestionsDTO>> getTopQuestions(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "25") int size) {
        Page<QuestionsDTO> topQuestions = questionService.getTopQuestions(page, size);
        return ResponseEntity.ok(topQuestions);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDTO>> getAllQuestionsForUser(@PathVariable @NotNull Long userId) throws UserNotFoundException {
        List<PostDTO> questions = postService.getAllPostsForUser(userId, PostType.QUESTION);
        return ResponseEntity.ok(questions);
    }
}
