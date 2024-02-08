package com.project.stackoverflow.service.Impl;

import com.project.stackoverflow.constant.PostStatus;
import com.project.stackoverflow.constant.PostType;
import com.project.stackoverflow.dto.*;
import com.project.stackoverflow.entity.*;
import com.project.stackoverflow.exception.*;
import com.project.stackoverflow.repository.*;
import com.project.stackoverflow.service.QuestionService;
import com.project.stackoverflow.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private UserService userService;

    @Mock
    private QuestionService questionService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private MediaRepository mediaRepository;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreatePostAsQuestion() throws UserNotFoundException, ParentQuestionNotFoundException, MultipleTagsFoundException {
        PostDTO createPostRequest = new PostDTO();
        createPostRequest.setUserId(1L);
        createPostRequest.setTitle("Test Question");
        createPostRequest.setContent("Question content");
        Set<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        createPostRequest.setTags(tags);
        List<MediaDTO> mediaList = List.of(
                new MediaDTO(1L, "url1"),
                new MediaDTO(2L, "url2")
        );
        createPostRequest.setMedia(mediaList);

        UserInfo user = new UserInfo();
        user.setId(1L);
        when(userService.getUserDetails(createPostRequest.getUserId())).thenReturn(Optional.of(user));

        Post post = new Post();
        post.setId(1L);
        post.setStatus(PostStatus.OPEN);
        post.setPostType(PostType.QUESTION);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDTO result = postService.createPost(createPostRequest);

        assertNotNull(result);
        assertEquals(createPostRequest.getTitle(), result.getTitle());
        assertEquals(createPostRequest.getContent(), result.getContent());
        assertEquals("QUESTION", result.getPostType());
        assertEquals("OPEN", result.getStatus());
        assertNotNull(result.getMedia());
    }

    @Test
    void testCreatePostAsAnswer() throws UserNotFoundException, ParentQuestionNotFoundException, MultipleTagsFoundException {
        PostDTO createPostRequest = new PostDTO();
        createPostRequest.setUserId(1L);
        createPostRequest.setContent("Answer content");
        createPostRequest.setParentId(2L);

        UserInfo user = new UserInfo();
        user.setId(1L);
        when(userService.getUserDetails(createPostRequest.getUserId())).thenReturn(Optional.of(user));

        Post parentQuestion = new Post();
        parentQuestion.setId(2L);
        parentQuestion.setPostType(PostType.QUESTION);
        when(postRepository.findById(2L)).thenReturn(Optional.of(parentQuestion));

        Post post = new Post();
        post.setId(1L);
        post.setStatus(PostStatus.CREATED);
        post.setPostType(PostType.ANSWER);
        post.setParentQuestion(parentQuestion);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDTO result = postService.createPost(createPostRequest);

        assertNotNull(result);
        assertEquals(createPostRequest.getTitle(), result.getTitle());
        assertEquals(createPostRequest.getContent(), result.getContent());
        assertEquals("ANSWER", result.getPostType());
        assertEquals("CREATED", result.getStatus());
        assertNotNull(result.getParentQuestion());
        assertEquals(2L, result.getParentId());
    }

    @Test
    void testCreatePostUserNotFoundException() {
        PostDTO createPostRequest = new PostDTO();
        createPostRequest.setUserId(1L);

        when(userService.getUserDetails(createPostRequest.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            postService.createPost(createPostRequest);
        });
    }
}
