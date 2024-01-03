package com.freshvotes.repositories;

import com.freshvotes.domain.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature,Long> {
    Feature findFeatureById(Long featureId);

}
