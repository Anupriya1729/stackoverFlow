package com.project.stackoverflow.dto;

import com.project.stackoverflow.constant.PostType;
import com.project.stackoverflow.entity.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private String postType;
    private Integer upvoteCount;
    private Integer downvoteCount;
    private String status;
    private String creationDate;
    private String modificationDate;
    private Set<String> tags;
    private List<MediaDTO> media;
    private Long parentId;
    private PostDTO parentQuestion;
    private String message;

    public PostDTO(
            Long postId,
            Long userId,
            String title,
            String content,
            String postType,
            Integer upvoteCount,
            Integer downvoteCount,
            String status,
            String creationDate,
            String modificationDate,
            Set<String> tags) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.upvoteCount = upvoteCount;
        this.downvoteCount = downvoteCount;
        this.status = status;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.tags = tags;
    }

    public PostDTO(String message) {
        this.message = message;
    }
}
