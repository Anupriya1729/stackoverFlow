package com.project.stackoverflow.service;

import com.project.stackoverflow.dto.PostDTO;

import java.util.List;

public interface AnswerService {
    List<PostDTO> getAllAnswersForQuestion(Long questionId);
}
