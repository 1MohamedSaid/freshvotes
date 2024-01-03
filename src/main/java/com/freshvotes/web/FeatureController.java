package com.freshvotes.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import com.freshvotes.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.service.FeatureService;

@Controller
@RequestMapping("/products/{productId}/features")
public class FeatureController {

    Logger log = LoggerFactory.getLogger(FeatureController.class);
    @Autowired
    CommentService commentService;
    @Autowired
    private FeatureService featureService;

    @PostMapping
    public String createFeature(@AuthenticationPrincipal User user, @PathVariable Long productId) {
        Feature feature = featureService.createFeature(productId, user);
        return "redirect:/products/" + productId + "/features/" + feature.getId();
    }

    @GetMapping("{featureId}")
    public String getFeature(@AuthenticationPrincipal User user, ModelMap model, @PathVariable Long productId, @PathVariable Long featureId) {
        Feature feature = featureService.findFeatureById(featureId);
        Set<Comment> comments = commentService.getSortedComments(featureId);
        model.put("feature", feature);
        model.put("thread", comments);
        model.put("comment", new Comment());
        model.put("user", user);
        return "feature";
    }


    @PostMapping("{featureId}")
    public String updateFeature(@AuthenticationPrincipal User user, Feature feature, @PathVariable Long productId, @PathVariable Long featureId) {
        featureService.updateFeature(feature,user);
        String encodedProductName;
        try {
            encodedProductName = URLEncoder.encode(feature.getProduct().getName(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.warn("Unable to encode the URL string: " + feature.getProduct().getName() + ", redirecting to dashboard instead of the intended product user view page.");
            return "redirect:/dashboard";
        }
        return "redirect:/p/" + encodedProductName;
    }
    @PostMapping("/{featureId}/upvote")
    public String upvoteFeature( @AuthenticationPrincipal User user, @PathVariable Long featureId, @PathVariable String productId) {
        featureService.upvoteFeature(featureId,user);
        return "redirect:/products/{productId}/features/" + featureId;
    }

    @PostMapping("/{featureId}/downvote")
    public String downvoteFeature( @AuthenticationPrincipal User user, @PathVariable Long featureId, @PathVariable String productId) {
        featureService.downvoteFeature(featureId,user);
        return "redirect:/products/{productId}/features/" + featureId;
    }
}