package com.project.stackoverflow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDTO {
    private String content;
    private Long commentId;
    @NotNull(message = "PostId cannot be null.")
    private Long postId;
    private Long userId;
    private Long creationDate;
    private String error;

    public static CommentDTO withError(String error) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setError(error);
        return commentDTO;
    }
}

