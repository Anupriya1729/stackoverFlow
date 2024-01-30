package com.project.stackoverflow.service.Impl;

import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.entity.Post;
import com.project.stackoverflow.repository.PostRepository;
import com.project.stackoverflow.service.AnswerService;
import com.project.stackoverflow.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<PostDTO> getAllAnswersForQuestion(Long questionId) {
        List<Post> answers = postRepository.findAnswersForQuestion(questionId);
        return Mapper.mapPostsToPostDTOList(answers);
    }
}
