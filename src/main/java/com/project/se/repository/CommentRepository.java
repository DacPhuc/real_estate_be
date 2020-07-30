package com.project.se.repository;

import com.project.se.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = "SELECT * FROM comment c where c.estate_id = :id", nativeQuery=true)
    List<Comment> findByEstate_id(@Param("id") Integer id);
}
