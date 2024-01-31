package com.project.stackoverflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.stackoverflow.constant.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "phone"})
})
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private int reputationPoints;
    private String name;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Comment> comments;

    public void addComment(Comment comment) {
        if (Objects.nonNull(comment)) {
            if (Objects.isNull(comments)) {
                comments = new ArrayList<>();
            }
            comments.add(comment);
            comment.setUser(this);
        }
    }

    public void addPost(Post post) {
        if(Objects.nonNull(post)){
            if(Objects.isNull(posts)){
                posts = new ArrayList<>();
            }
            posts.add(post);
            post.setUser(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return getId() != null && Objects.equals(getId(), userInfo.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", reputationPoints=" + reputationPoints +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", username='" + username + '\'' +
                '}';
    }

}