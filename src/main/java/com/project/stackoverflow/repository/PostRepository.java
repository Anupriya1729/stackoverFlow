package com.project.stackoverflow.repository;

import com.project.stackoverflow.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.postType = com.project.stackoverflow.constant.PostType.QUESTION ORDER BY SIZE(p.votes) DESC")
    Page<Post> findTopQuestions(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE LOWER(p.content) LIKE %:keyword%")
    List<Post> searchPostsByKeyword(String keyword);
}
