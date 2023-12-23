package com.freshvotes.service;

import com.freshvotes.domain.Comment;
import com.freshvotes.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public Set<Comment> getSortedComments(Long featureId) {
        return commentRepository.findByCommentIsNullAndFeatureId(featureId);
    }
}
