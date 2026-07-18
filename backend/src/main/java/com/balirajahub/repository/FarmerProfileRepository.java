package com.balirajahub.repository;

import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerProfileRepository extends JpaRepository<FarmerProfile, Long> {

    Optional<FarmerProfile> findByUser(User user);

    boolean existsByUser(User user);
}