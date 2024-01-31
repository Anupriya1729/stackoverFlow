package com.project.stackoverflow.service.Impl;

import com.project.stackoverflow.constant.PostType;
import com.project.stackoverflow.constant.VoteType;
import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.dto.QuestionsDTO;
import com.project.stackoverflow.entity.Post;
import com.project.stackoverflow.entity.Tag;
import com.project.stackoverflow.entity.UserInfo;
import com.project.stackoverflow.exception.MultipleTagsFoundException;
import com.project.stackoverflow.exception.PostNotFoundException;
import com.project.stackoverflow.exception.UserNotFoundException;
import com.project.stackoverflow.repository.PostRepository;
import com.project.stackoverflow.repository.TagRepository;
import com.project.stackoverflow.service.QuestionService;
import com.project.stackoverflow.service.UserService;
import com.project.stackoverflow.util.HelperMethods;
import com.project.stackoverflow.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.stackoverflow.util.Mapper.mapToPostDTO;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;


    public Page<QuestionsDTO> getTopQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> topQuestionsPage = postRepository.findTopQuestions(pageable);

        return topQuestionsPage.map(post -> {
            UserInfo author = post.getUser();
            Set<Tag> tags = post.getTags();

            int upvotes = HelperMethods.countVotes(post, VoteType.UPVOTE);
            int downvotes = HelperMethods.countVotes(post, VoteType.DOWNVOTE);
            int priority = upvotes + downvotes; //Engagement

            return new QuestionsDTO(post.getId(), post.getTitle(), post.getContent(), post.getCreationDate(),
                    author.getName(), author.getId(), tags, upvotes, downvotes, priority);
        });
    }

    public PostDTO addTagsToQuestion(Long questionId, Set<String> tags, boolean isNewQuestion, Post post)
            throws PostNotFoundException, MultipleTagsFoundException {
        if (!isNewQuestion) {
            post = postRepository.findById(questionId)
                    .orElseThrow(() -> new PostNotFoundException(questionId.toString()));
        }

        if (tags != null) {
            Set<Tag> tagSet = tags.stream().map(tagName -> {
                List<Tag> existingTags = tagRepository.findByTagName(tagName.toLowerCase());

                if (existingTags.isEmpty()) {
                    // Create a new tag if none exists with the given name
                    Tag newTag = new Tag();
                    newTag.setTagName(tagName);
                    return tagRepository.save(newTag);
                } else if (existingTags.size() == 1) {
                    // Use the existing tag if there's exactly one match
                    return existingTags.get(0);
                } else {
                    throw new MultipleTagsFoundException(tagName);
                }
            }).collect(Collectors.toSet());

            post.setTags(tagSet);
        }

        Post savedPost = postRepository.save(post);
        return mapToPostDTO(savedPost);
    }
}
