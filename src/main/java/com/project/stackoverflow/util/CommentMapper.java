package com.project.stackoverflow.util;

import com.project.stackoverflow.dto.CommentDTO;
import com.project.stackoverflow.entity.Comment;

public class CommentMapper {
    public static CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent(comment.getText());
        commentDTO.setPostId(comment.getPost().getId());
        commentDTO.setCreationDate(commentDTO.getCreationDate());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setPostId(comment.getPost().getId());
        return commentDTO;
    }
}
