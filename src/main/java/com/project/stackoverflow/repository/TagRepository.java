package com.project.stackoverflow.repository;

import com.project.stackoverflow.entity.Post;
import com.project.stackoverflow.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT DISTINCT p FROM Post p JOIN p.tags t WHERE LOWER(t.tagName) = LOWER(:tagName)")
    List<Post> findAllPostsByTagName(@Param("tagName") String tagName);

    List<Tag> findByTagName(String tag);

    Set<Tag> findByTagNameIn(Set<String> tagNames);
}