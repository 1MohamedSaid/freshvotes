package com.freshvotes.service;

import com.freshvotes.domain.*;
import com.freshvotes.repositories.FeatureRepository;
import com.freshvotes.repositories.ProductRepository;
import com.freshvotes.repositories.UserRepository;
import com.freshvotes.repositories.VoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Optional;

@Service
public class FeatureService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private FeatureRepository featureRepo;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;

    public Feature createFeature(Long productId, User user) {
        Feature feature = new Feature();
        Product product = productRepo.findProductById(productId);
        feature.setProduct(product);

        product.getFeatures().add(feature);

        feature.setUser(user);
        feature.setVotes(feature.getVotes());

        feature.setStatus("Pending review");
        featureRepo.save(feature);
        return feature;
    }
    public Feature findFeatureById(Long featureId) {
        return featureRepo.findFeatureById(featureId);
    }

    public void updateFeature(Feature currentFeature, User user) {
        Feature updatedFeature = featureRepo.findFeatureById(currentFeature.getId());
        Integer existingVoteCount = updatedFeature.getVoteCount();
        updatedFeature.setUser(user);
        updatedFeature.setTitle(currentFeature.getTitle());
        updatedFeature.setDescription(currentFeature.getDescription());
        updatedFeature.setVoteCount(existingVoteCount);
        user.setFeatures(Arrays.asList(updatedFeature));
        featureRepo.save(updatedFeature);
    }

    public void upvoteFeature(Long featureId, User user) {
        user = userRepository.findByUsername(user.getName());
        Optional<Feature> feature = featureRepo.findById(featureId);
        Optional<Vote> existingVote = voteRepository.findByUserAndFeature(user, feature.get());
        if (existingVote.isPresent()) {
            if (existingVote.get().getVoteType()) {
                voteRepository.delete(existingVote.get());
                feature.get().setVoteCount(feature.get().getVoteCount() - 1);
                featureRepo.save(feature.get());
            } else {
                existingVote.get().setVoteType(true);
                feature.get().setVoteCount(feature.get().getVoteCount() + 2);
                voteRepository.save(existingVote.get());
                featureRepo.save(feature.get());
            }
        } else {
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setFeature(feature.get());
            vote.setVoteType(true);
            feature.get().setVoteCount(feature.get().getVoteCount() + 1);
            voteRepository.save(vote);
            featureRepo.save(feature.get());
        }
    }
    public void downvoteFeature(Long featureId, User user) {
        user = userRepository.findByUsername(user.getName());
        Optional<Feature> feature = featureRepo.findById(featureId);
        Optional<Vote> existingVote = voteRepository.findByUserAndFeature(user, feature.get());
        if (existingVote.isPresent()) {
            if (existingVote.get().getVoteType()) {
                existingVote.get().setVoteType(false);
                feature.get().setVoteCount(feature.get().getVoteCount() - 2);
                voteRepository.save(existingVote.get());
                featureRepo.save(feature.get());
            } else {
                voteRepository.delete(existingVote.get());
                feature.get().setVoteCount(feature.get().getVoteCount() + 1);
                featureRepo.save(feature.get());
            }
        } else {
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setFeature(feature.get());
            vote.setVoteType(false);
            feature.get().setVoteCount(feature.get().getVoteCount() - 1);
            voteRepository.save(vote);
            featureRepo.save(feature.get());
        }
    }
}
