package com.freshvotes.web;

import com.freshvotes.domain.*;
import com.freshvotes.repositories.CommentRepository;
import com.freshvotes.repositories.FeatureRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.SortedSet;

@Controller
@RequestMapping("/products/{productId}/features/{featureId}/comments")
public class CommentController {
    @Autowired
    CommentRepository commentRepo;
    @Autowired
    FeatureRepository featureRepo;


    @GetMapping("")
    @ResponseBody
    public SortedSet<Comment> getComments(@PathVariable Long featureId, @PathVariable Long productId) {
        return commentRepo.findByCommentIsNullAndFeatureId(featureId);

    }

    @PostMapping("")
    public String Comment(@AuthenticationPrincipal User user,
                          @PathVariable Long productId,
                          @PathVariable Long featureId,
                          Comment rootComment,
                          @RequestParam(required = false) Long parentId,
                          @RequestParam(required = false) String childCommentText) {

        Feature feature = featureRepo.findFeatureById(featureId); //if base comment
        if (!StringUtils.isEmpty(rootComment.getText())) {
            populateCommentMetadata(user, feature, rootComment);
            commentRepo.save(rootComment); //if base comment

        } else if (parentId != null) { //if reply
            Comment comment = new Comment();
            Comment parentComment = commentRepo.findCommentById(parentId);
            comment.setComment(parentComment);
            comment.setText(childCommentText);
            populateCommentMetadata(user, feature, comment);
            commentRepo.save(comment);
        }// if reply
        return "redirect:/products/" + productId + "/features/" + featureId;
    }

    private void populateCommentMetadata(User user, Feature feature, Comment comment) {
        comment.setFeature(feature);
        comment.setUser(user);
        comment.setCreatedOn(new Date());
    }


    @GetMapping("/{id}")
    public String getComment(@PathVariable Long commentId, Model model) throws NotFoundException {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new NotFoundException("Post not found"));
        model.addAttribute("comment", comment);
        return "feature";
    }
}

