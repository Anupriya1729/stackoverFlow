package com.project.stackoverflow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDTO postDTO = (PostDTO) o;
        return Objects.equals(postId, postDTO.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }
}
