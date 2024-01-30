package com.project.stackoverflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.stackoverflow.constant.PostStatus;
import com.project.stackoverflow.constant.PostType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserInfo user;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @ManyToOne
    @JoinColumn(name = "parent_question_id", referencedColumnName = "post_id")
    private Post parentQuestion;

    @OneToMany(mappedBy = "parentQuestion")
    private List<Post> childPosts;

    private String title;

    private String content;

    private LocalDateTime creationDate;

    private LocalDateTime modificationDate;

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @OneToMany(mappedBy = "post", cascade=CascadeType.ALL)
    private List<Media> media;

    @OneToMany(mappedBy = "post")
    private List<Vote> votes;

    public void addComment(Comment comment) {
        if (Objects.nonNull(comment)) {
            if (Objects.isNull(comments)) {
                comments = new ArrayList<>();
            }
            comments.add(comment);
            comment.setPost(this);
        }
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", postType=" + postType +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", status=" + status +
                '}';
    }
}
