package com.project.stackoverflow.service;

import com.project.stackoverflow.constant.PostType;
import com.project.stackoverflow.dto.CommentDTO;
import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.exception.MultipleTagsFoundException;
import com.project.stackoverflow.exception.ParentQuestionNotFoundException;
import com.project.stackoverflow.exception.PostNotFoundException;
import com.project.stackoverflow.exception.UserNotFoundException;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO createPostRequest) throws UserNotFoundException, ParentQuestionNotFoundException, MultipleTagsFoundException;
    PostDTO getPostById(Long postId) throws PostNotFoundException;
    PostDTO upvotePost(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException;
    PostDTO downvotePost(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException;
    CommentDTO addComment(CommentDTO commentDTO) throws PostNotFoundException, UserNotFoundException;
    List<PostDTO> getAllPostsForUser(Long userId, PostType postType) throws UserNotFoundException;
}
