package com.freshvotes.web;

import com.freshvotes.domain.User;
import com.freshvotes.repositories.CommentRepository;
import com.freshvotes.repositories.UserRepository;
import com.freshvotes.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class VoteController {
    @Autowired
    VoteService voteService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/products/{productId}/features/{featureId}/upvote/{commentId}")
    public String upvote(@PathVariable Long commentId, @AuthenticationPrincipal User user, @PathVariable Long featureId, @PathVariable String productId) {
        voteService.upvote(commentId,user);
        return "redirect:/products/{productId}/features/" + featureId;
    }

    @PostMapping("/products/{productId}/features/{featureId}/downvote/{commentId}")
    public String downvote(@PathVariable Long commentId, @AuthenticationPrincipal User user, @PathVariable Long featureId, @PathVariable String productId) {
        voteService.downvote(commentId,user);
        return "redirect:/products/{productId}/features/" + featureId;
    }
}
