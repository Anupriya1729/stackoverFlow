package com.project.stackoverflow.service.Impl;

import com.project.stackoverflow.constant.PostStatus;
import com.project.stackoverflow.constant.PostType;
import com.project.stackoverflow.dto.MediaDTO;
import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.entity.Media;
import com.project.stackoverflow.entity.Post;
import com.project.stackoverflow.entity.UserInfo;
import com.project.stackoverflow.repository.MediaRepository;
import com.project.stackoverflow.repository.PostRepository;
import com.project.stackoverflow.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class PostServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MediaRepository mediaRepository;

    @Mock
    private UserInfoRepository userRepository;

    @Test
    void testCreatePost() throws Exception {
        // Arrange
        MockitoAnnotations.openMocks(this); // Initializes annotated fields

        // Mock user details
        UserInfo mockUser = new UserInfo();
        mockUser.setId(1L);
        when(userService.getUserDetails(any(Long.class))).thenReturn(Optional.of(mockUser));


        // Mock parent question
        Post mockParentQuestion = new Post();
        mockParentQuestion.setId(100L);
        when(postRepository.findById(any())).thenReturn(Optional.of(mockParentQuestion));

        // Mock media request
        MediaDTO mockMediaRequest = new MediaDTO();
        mockMediaRequest.setUrl("http://example.com/image.jpg");

        // Mock createPostRequest
        PostDTO createPostRequest = new PostDTO();
        createPostRequest.setUserId(1L);
        createPostRequest.setTitle("Test Title");
        createPostRequest.setContent("Test Content");
        createPostRequest.setMedia(Collections.singletonList(mockMediaRequest));

        // Mock post saved in repository
        Post savedPost = new Post();
        savedPost.setId(101L);
        savedPost.setTitle(createPostRequest.getTitle());
        savedPost.setContent(createPostRequest.getContent());
        savedPost.setUser(mockUser);
        savedPost.setCreationDate(LocalDateTime.now());
        savedPost.setModificationDate(savedPost.getCreationDate());
        savedPost.setMedia(new ArrayList<>());
        savedPost.setComments(new ArrayList<>());
        savedPost.setStatus(PostStatus.OPEN);
        savedPost.setPostType(PostType.QUESTION);

        // Mock the media saved in repository
        Media savedMedia = new Media();
        savedMedia.setId(201L);
        savedMedia.setUrl(mockMediaRequest.getUrl());
        savedMedia.setPost(savedPost);

        when(postRepository.save(any())).thenReturn(savedPost);
        when(mediaRepository.save(any())).thenReturn(savedMedia);

        // Act
        PostDTO result = postService.createPost(createPostRequest);

        // Assert
        assertNotNull(result);
        assertEquals(savedPost.getId(), result.getId());
        verify(userService, times(1)).getUserDetails(eq(1L));

        if (createPostRequest.getParentId() == null) {
            assertEquals(PostStatus.OPEN, savedPost.getStatus());
            assertEquals(PostType.QUESTION, savedPost.getPostType());
        } else {
            assertEquals(PostType.ANSWER, savedPost.getPostType());
        }
    }
}

