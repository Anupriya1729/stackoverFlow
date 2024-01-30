package com.project.stackoverflow.service;

import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.dto.QuestionsDTO;
import com.project.stackoverflow.entity.Post;
import com.project.stackoverflow.exception.MultipleTagsFoundException;
import com.project.stackoverflow.exception.PostNotFoundException;
import com.project.stackoverflow.exception.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    Page<QuestionsDTO> getTopQuestions(int page, int size);
    PostDTO addTagsToQuestion(Long questionId, Set<String> tags, boolean isNewQuestion, Post post) throws PostNotFoundException, MultipleTagsFoundException;
}
