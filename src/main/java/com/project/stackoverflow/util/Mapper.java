package com.project.stackoverflow.util;

import com.project.stackoverflow.constant.PostType;
import com.project.stackoverflow.constant.VoteType;
import com.project.stackoverflow.dto.MediaDTO;
import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.dto.QuestionsDTO;
import com.project.stackoverflow.entity.Post;
import com.project.stackoverflow.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public static PostDTO mapToPostDTO(Post post) {
        if (post == null) {
            return null;
        }

        PostDTO postDTO = new PostDTO(
                post.getId(),
                post.getUser() != null ? post.getUser().getId() : null,
                post.getTitle(),
                post.getContent(),
                post.getPostType() != null ? post.getPostType().name() : null,
                HelperMethods.countVotes(post, VoteType.UPVOTE),
                HelperMethods.countVotes(post, VoteType.DOWNVOTE),
                post.getStatus() != null ? post.getStatus().name() : null,
                post.getCreationDate() != null? post.getCreationDate().toString(): null,
                post.getModificationDate() != null? post.getModificationDate().toString(): null,
                post.getTags() != null ? post.getTags().stream().map(Tag::getTagName).collect(Collectors.toSet()) : null );

        if(PostType.ANSWER.equals(post.getPostType())){
            postDTO.setParentId(post.getParentQuestion().getId());
        }

        if (post.getMedia() != null && !post.getMedia().isEmpty()) {
            postDTO.setMedia(post.getMedia().stream().filter(media -> media != null).map(media -> {
                MediaDTO mediaDTO = new MediaDTO();
                if (media != null) {
                    mediaDTO.setMediaId(media.getId());
                    mediaDTO.setUrl(media.getUrl());
                }
                return mediaDTO;
            }).toList());
        }

        return postDTO;
    }

    public static List<PostDTO> mapPostsToPostDTOList(List<Post> posts) {
        return posts.stream()
                .map(Mapper::mapToPostDTO)
                .collect(Collectors.toList());
    }

    public static QuestionsDTO mapPostToQuestionsDTO(Post post) {
        QuestionsDTO questionsDTO = new QuestionsDTO();
        questionsDTO.setQuestionId(post.getId());
        questionsDTO.setHeading(post.getTitle());
        questionsDTO.setBody(post.getContent());
        questionsDTO.setCreationDate(post.getCreationDate());
        questionsDTO.setAuthorName(post.getUser().getName());
        questionsDTO.setAuthorId(post.getUser().getId());
        questionsDTO.setTags(post.getTags());
        questionsDTO.setUpvotes(HelperMethods.countVotes(post, VoteType.UPVOTE));
        questionsDTO.setDownvotes(HelperMethods.countVotes(post, VoteType.DOWNVOTE));
        return questionsDTO;
    }

    public static List<QuestionsDTO> mapPostsToQuestionsDTOList(List<Post> posts) {
        return posts.stream()
                .map(Mapper::mapPostToQuestionsDTO)
                .collect(Collectors.toList());
    }
}

