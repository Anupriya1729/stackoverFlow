package com.project.stackoverflow.entity;

import com.project.stackoverflow.constant.VoteType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    private LocalDateTime createdDate;

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", voteType=" + voteType +
                ", createdDate=" + createdDate +
                '}';
    }
}
