package com.freshvotes.repositories;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findByUserAndComment(User user, Comment comment);
    Optional<Vote> findByUserAndFeature(User user, Feature feature);
    List<Vote> findAllByFeature(Feature feature);
}
