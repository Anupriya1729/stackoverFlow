package com.project.stackoverflow.dto;

import com.project.stackoverflow.entity.Tag;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsDTO {
    private Long questionId;
    private String heading;
    private String body;
    private LocalDateTime creationDate;
    private String authorName;
    private Long authorId;
    private Set<Tag> tags;
    private int upvotes;
    private int downvotes;
    private int priority;
}
