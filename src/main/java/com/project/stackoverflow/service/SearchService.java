package com.project.stackoverflow.service;

import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.dto.QuestionsDTO;
import com.project.stackoverflow.entity.Post;

import java.util.List;
import java.util.Set;

public interface SearchService {
    List<QuestionsDTO> getAllQuestionsByTagName(String tagName);
    Set<PostDTO> searchPostsByString(String words);
}