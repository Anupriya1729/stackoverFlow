package com.project.stackoverflow.service.Impl;

import com.project.stackoverflow.constant.PostType;
import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.dto.QuestionsDTO;
import com.project.stackoverflow.entity.Post;
import com.project.stackoverflow.repository.PostRepository;
import com.project.stackoverflow.repository.TagRepository;
import com.project.stackoverflow.service.SearchService;
import com.project.stackoverflow.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.project.stackoverflow.util.Mapper.mapToPostDTO;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    public static Set<String> extractKeywords(String sentence) {
        Set<String> notNeeded = new HashSet<>(Arrays.asList("is", "the", "that", "what", "in", "a", "an", "and", "of"));

        // Compile a regex pattern to remove non-alphabetic characters
        Pattern nonAlphabeticPattern = Pattern.compile("[^a-zA-Z]");

        return Arrays.stream(sentence.split("\\s+"))
                .map(word -> nonAlphabeticPattern.matcher(word).replaceAll("").toLowerCase())
                .filter(word -> !notNeeded.contains(word))
                .collect(Collectors.toSet());
    }

    @Override
    public List<QuestionsDTO> getAllQuestionsByTagName(String tagName) {
        List<Post> posts =  tagRepository.findAllPostsByTagName(tagName);
        return posts.stream().map(Mapper::mapPostToQuestionsDTO).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> searchPostsByString(String words) {
        //Users can search questions/answers by text
        //user enters a string
        //we search all the questions/answer that contain the string in content in the POST table
        //if case of answer, we fetch the question linked to the answer

        Set<String> keywords = extractKeywords(words);

        List<PostDTO> matchingPosts = new ArrayList<>();

        // Search for posts using each keyword
        for (String keyword : keywords) {
            List<Post> postsForCurrentKeyword = postRepository.searchPostsByKeyword(keyword);
            //CREATE INDEX idx_content ON Post(content);

            // Map each Post to PostDTO and add to matchingPosts
            matchingPosts.addAll(postsForCurrentKeyword.stream()
                    .map(post -> {
                        PostDTO postDTO = mapToPostDTO(post);

                        // If the post is an answer, fetch its parent question
                        if (post.getPostType() == PostType.ANSWER) {
                            Post parentQuestion = post.getParentQuestion();
                            if (parentQuestion != null) {
                                PostDTO parentQuestionDTO = mapToPostDTO(parentQuestion);
                                postDTO.setParentQuestion(parentQuestionDTO);
                            }
                        }

                        return postDTO;
                    })
                    .toList());
        }

        log.debug("Extracted Keywords: " + keywords + "Matching post: " + matchingPosts.size());
        return matchingPosts;
    }
}
