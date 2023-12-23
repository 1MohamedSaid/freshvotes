package com.freshvotes.repositories;

import com.freshvotes.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.SortedSet;

public interface CommentRepository extends JpaRepository<Comment,Long>{
    SortedSet<Comment> findByCommentIsNullAndFeatureId(Long featureId);
    Comment findCommentById(Long commentId);

}
