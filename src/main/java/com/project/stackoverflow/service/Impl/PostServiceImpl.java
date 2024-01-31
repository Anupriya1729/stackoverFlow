package com.project.stackoverflow.service.Impl;

import com.project.stackoverflow.constant.PostStatus;
import com.project.stackoverflow.constant.PostType;
import com.project.stackoverflow.constant.VoteType;
import com.project.stackoverflow.dto.CommentDTO;
import com.project.stackoverflow.dto.MediaDTO;
import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.entity.*;
import com.project.stackoverflow.exception.MultipleTagsFoundException;
import com.project.stackoverflow.exception.ParentQuestionNotFoundException;
import com.project.stackoverflow.exception.PostNotFoundException;
import com.project.stackoverflow.exception.UserNotFoundException;
import com.project.stackoverflow.repository.*;
import com.project.stackoverflow.service.PostService;
import com.project.stackoverflow.service.QuestionService;
import com.project.stackoverflow.service.UserService;
import com.project.stackoverflow.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.stackoverflow.util.CommentMapper.mapToCommentDTO;
import static com.project.stackoverflow.util.Mapper.mapToPostDTO;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private CommentRepository commentRepository;

    public PostDTO createPost(PostDTO createPostRequest) throws UserNotFoundException, ParentQuestionNotFoundException, MultipleTagsFoundException {
        Optional<UserInfo> user = userService.getUserDetails(createPostRequest.getUserId());

        if (user.isEmpty()) {
            throw new UserNotFoundException(createPostRequest.getUserId().toString());
        }

        Post post = new Post();

        if (createPostRequest.getParentId() == null) {
            post.setStatus(PostStatus.OPEN);
            post.setPostType(PostType.QUESTION);
        } else {
            post.setPostType(PostType.ANSWER);
            post.setStatus(PostStatus.CREATED);
            handleParentQuestion(post, createPostRequest.getParentId());
        }

        post.setTitle(createPostRequest.getTitle());
        post.setContent(createPostRequest.getContent());
        post.setUser(user.get());
        post.setCreationDate(LocalDateTime.now());
        post.setModificationDate(post.getCreationDate());
        post.setMedia(null);
        post.setComments(new ArrayList<>());
        post.setVotes(new ArrayList<>());

        saveMedia(post, createPostRequest.getMedia());

        post = postRepository.save(post);

        if (PostType.QUESTION.equals(post.getPostType())) {
            questionService.addTagsToQuestion(post.getId(), createPostRequest.getTags(), true, post);
        }

        // Map the post to PostDTO
        PostDTO postDTO = mapToPostDTO(post);
        if (PostType.ANSWER.equals(post.getPostType())) {
            postDTO.setParentQuestion(mapToPostDTO(post.getParentQuestion()));
            postDTO.setParentId(post.getParentQuestion().getId());
        }

        user.get().addPost(post);

        return postDTO;
    }

    private void handleParentQuestion(Post post, Long parentId) throws ParentQuestionNotFoundException {
        if (parentId != null) {
            Optional<Post> parentQuestion = postRepository.findById(parentId);
            if (parentQuestion.isEmpty()) {
                throw new ParentQuestionNotFoundException(parentId.toString());
            }
            post.setParentQuestion(parentQuestion.get());
        }
    }

    private void saveMedia(Post post, List<MediaDTO> mediaRequests) {
        if (mediaRequests != null) {
            List<Media> mediaList = mediaRequests.stream()
                    .map(mediaRequest -> {
                        Media media = new Media();
                        media.setUrl(mediaRequest.getUrl());
                        media.setPost(post);
                        return mediaRepository.save(media);
                    })
                    .collect(Collectors.toList());

            post.setMedia(mediaList);
        }
    }

    public PostDTO getPostById(Long postId) throws PostNotFoundException {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            throw new PostNotFoundException(postId.toString());
        }

        Post post = postOptional.get();
        PostDTO postDTO = mapToPostDTO(post);

        if (PostType.ANSWER.equals(post.getPostType())) {
            Post parentQuestion = post.getParentQuestion();

            if (parentQuestion != null) {
                postDTO.setParentId(parentQuestion.getId());
                PostDTO parentQuestionDTO = mapToPostDTO(parentQuestion);
                postDTO.setParentQuestion(parentQuestionDTO);
            }
        }

        return postDTO;
    }

    public Post findPostById(Long postId) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(postId);
        return post.get();
    }

    public PostDTO upvotePost(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException {
        return vote(postId, userId, VoteType.UPVOTE);
    }

    public PostDTO downvotePost(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException {
        return vote(postId, userId, VoteType.DOWNVOTE);
    }

    private PostDTO vote(Long postId, Long userId, VoteType voteType) throws PostNotFoundException, UserNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));

        UserInfo user = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));

        Vote votes = voteRepository.findByPostIdAndUserId(postId, userId);

        if (votes != null) {
            // If the user has already voted for this post
            VoteType existingVoteType = votes.getVoteType();
            if (existingVoteType != voteType) {
                // If the user is trying to vote the same type again, do nothing (optional)
                // If the user is trying to change the vote type, delete existing votes and add a new vote
                voteRepository.delete(votes);
                votes = saveVote(post, user, voteType);
            }
        } else {
            // If the user has not voted before, create a new vote
            votes = saveVote(post, user, voteType);
        }

        post.getVotes().add(votes);
        return mapToPostDTO(post);
    }

    public Vote saveVote(Post post, UserInfo user, VoteType voteType) {
        Vote newVote = new Vote();
        newVote.setPost(post);
        newVote.setUser(user);
        newVote.setVoteType(voteType);
        newVote.setCreatedDate(LocalDateTime.now());

        // Save the new vote to the database
        return voteRepository.save(newVote);
    }

    public CommentDTO addComment(CommentDTO commentDTO) throws PostNotFoundException, UserNotFoundException {
        Optional<Post> optionalPost = postRepository.findById(commentDTO.getPostId());

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            Comment comment = new Comment();
            comment.setText(commentDTO.getContent());
            comment.setPost(post);
            Optional<UserInfo> user = userInfoRepository.findById(commentDTO.getUserId());
            if (user.isEmpty()){
                throw new UserNotFoundException("null");
            }
            comment.setUser(user.get());
            comment.setCreationDate(LocalDateTime.now());
            comment.setModificationDate(LocalDateTime.now());
            post.addComment(comment);
            user.get().addComment(comment);
            commentRepository.save(comment);

            return mapToCommentDTO(comment);
        } else {
            throw new PostNotFoundException(commentDTO.getPostId().toString());
        }
    }

    public List<PostDTO> getAllPostsForUser(Long userId, PostType postType) throws UserNotFoundException {
        Optional<UserInfo> user = userService.getUserDetails(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException(userId.toString());
        }
        return user.get().getPosts().stream().filter(post -> post.getPostType().equals(postType)).map(Mapper::mapToPostDTO).toList();
    }
}
