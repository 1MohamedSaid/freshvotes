package com.freshvotes.service;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.User;
import com.freshvotes.domain.Vote;
import com.freshvotes.repositories.CommentRepository;
import com.freshvotes.repositories.UserRepository;
import com.freshvotes.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;

    public void upvote(Long commentId, User user) {
        user = userRepository.findByUsername(user.getName());
        Optional<Comment> comment = commentRepository.findById(commentId);
        Optional<Vote> existingVote = voteRepository.findByUserAndComment(user, comment.get());
        if (existingVote.isPresent()) {
            if (existingVote.get().getVoteType()) {
                voteRepository.delete(existingVote.get());
                comment.get().setVoteCount(comment.get().getVoteCount() - 1);
                commentRepository.save(comment.get());
            } else {
                existingVote.get().setVoteType(true);
                comment.get().setVoteCount(comment.get().getVoteCount() + 2);
                voteRepository.save(existingVote.get());
                commentRepository.save(comment.get());
            }
        } else {
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setComment(comment.get());
            vote.setVoteType(true);
            comment.get().setVoteCount(comment.get().getVoteCount() + 1);
            voteRepository.save(vote);
            commentRepository.save(comment.get());
        }
    }

    public void downvote(Long commentId, User user) {
        user = userRepository.findByUsername(user.getName());
        Optional<Comment> comment = commentRepository.findById(commentId);
        Optional<Vote> existingVote = voteRepository.findByUserAndComment(user, comment.get());
        if (existingVote.isPresent()) {
            if (existingVote.get().getVoteType()) {
                existingVote.get().setVoteType(false);
                comment.get().setVoteCount(comment.get().getVoteCount() - 2);
                voteRepository.save(existingVote.get());
                commentRepository.save(comment.get());
            } else {
                voteRepository.delete(existingVote.get());
                comment.get().setVoteCount(comment.get().getVoteCount() + 1);
                commentRepository.save(comment.get());
            }
        } else {
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setComment(comment.get());
            vote.setVoteType(false);
            comment.get().setVoteCount(comment.get().getVoteCount() - 1);
            voteRepository.save(vote);
            commentRepository.save(comment.get());
        }
    }
}
