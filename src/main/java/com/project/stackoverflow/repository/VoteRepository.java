package com.project.stackoverflow.repository;

import com.project.stackoverflow.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByPostIdAndUserId(Long postId, Long userId);
}