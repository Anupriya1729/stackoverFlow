package com.project.stackoverflow.service;

import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.dto.QuestionsDTO;
import com.project.stackoverflow.entity.Post;

import java.util.List;

public interface SearchService {
    List<QuestionsDTO> getAllQuestionsByTagName(String tagName);
    List<PostDTO> searchPostsByString(String words);
}