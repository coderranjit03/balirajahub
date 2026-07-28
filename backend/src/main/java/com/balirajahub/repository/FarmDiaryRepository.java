package com.balirajahub.repository;

import com.balirajahub.entity.FarmDiary;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.enums.DiaryActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FarmDiaryRepository
        extends JpaRepository<FarmDiary, Long> {

    List<FarmDiary> findByFarmerProfileOrderByEntryDateDesc(
            FarmerProfile farmerProfile
    );

    Optional<FarmDiary> findByIdAndFarmerProfile(
            Long id,
            FarmerProfile farmerProfile
    );

    List<FarmDiary> findByFarmerProfileAndEntryDate(
            FarmerProfile farmerProfile,
            LocalDate entryDate
    );

    List<FarmDiary> findByFarmerProfileAndActivityType(
            FarmerProfile farmerProfile,
            DiaryActivityType activityType
    );

    long countByFarmerProfile(FarmerProfile farmerProfile);


}