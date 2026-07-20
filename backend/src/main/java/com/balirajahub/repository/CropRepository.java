package com.balirajahub.repository;

import com.balirajahub.entity.Crop;
import com.balirajahub.entity.FarmerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CropRepository extends JpaRepository<Crop, Long> {

    List<Crop> findByFarmerProfile(FarmerProfile farmerProfile);

    Optional<Crop> findByIdAndFarmerProfile(
            Long id,
            FarmerProfile farmerProfile
    );
}