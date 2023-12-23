package com.freshvotes.service;

import com.freshvotes.domain.*;
import com.freshvotes.repositories.FeatureRepository;
import com.freshvotes.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class FeatureService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private FeatureRepository featureRepo;

    public Feature createFeature(Long productId, User user) {
        Feature feature = new Feature();
        Product product = productRepo.findProductById(productId);
        feature.setProduct(product);

        product.getFeatures().add(feature);

        feature.setUser(user);

        feature.setStatus("Pending review");
        featureRepo.save(feature);
        return feature;
    }


    public Feature findFeatureById(Long featureId) {
        return featureRepo.findFeatureById(featureId);
    }

    public void updateFeature(Feature feature, User user) {
        feature.setUser(user);
        user.setFeatures(Arrays.asList(feature));
        featureRepo.save(feature);
    }
}
