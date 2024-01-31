package com.project.stackoverflow.controller;

import com.project.stackoverflow.dto.CommentDTO;
import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.exception.MultipleTagsFoundException;
import com.project.stackoverflow.exception.ParentQuestionNotFoundException;
import com.project.stackoverflow.exception.PostNotFoundException;
import com.project.stackoverflow.exception.UserNotFoundException;
import com.project.stackoverflow.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO createPostRequest) throws UserNotFoundException, ParentQuestionNotFoundException, MultipleTagsFoundException {
        //createPost method to determine whether it's a question or an answer
        //based on the presence of the parentId in the request

//        @RequestParam("media") MultipartFile[] mediaFiles
//        List of media is stored in fileStorage and mediaUrls are stored in the DB
//        List<String> mediaUrls = Arrays.stream(mediaFiles)
//                .map(fileStorageService::storeFile)
//                .collect(Collectors.toList());

        PostDTO createdQuestion = postService.createPost(createPostRequest);
        return new ResponseEntity<>(createdQuestion, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) throws PostNotFoundException{
        if (postId == null) {
            throw new PostNotFoundException("null");
        }
        PostDTO response = postService.getPostById(postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{userId}/{postId}/upvote")
    public ResponseEntity<PostDTO> upvote(@PathVariable Long postId, @PathVariable Long userId) throws PostNotFoundException, UserNotFoundException {
        if (postId == null) {
            throw new PostNotFoundException("null");
        }
        PostDTO post = postService.upvotePost(postId, userId);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{userId}/{postId}/downvote")
    public ResponseEntity<PostDTO> downvoteQuestion(@PathVariable Long postId, @PathVariable Long userId) throws PostNotFoundException, UserNotFoundException {
        if (postId == null) {
            throw new PostNotFoundException("null");
        }
        PostDTO post = postService.downvotePost(postId, userId);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO) throws PostNotFoundException, UserNotFoundException{
        CommentDTO savedComment = postService.addComment(commentDTO);
        return new ResponseEntity<>(savedComment, HttpStatus.OK);
    }
}
