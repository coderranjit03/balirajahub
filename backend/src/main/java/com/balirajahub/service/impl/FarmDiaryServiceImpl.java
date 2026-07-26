package com.balirajahub.service.impl;

import com.balirajahub.dto.request.FarmDiaryRequest;
import com.balirajahub.dto.response.FarmDiaryResponse;
import com.balirajahub.entity.FarmDiary;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.User;
import com.balirajahub.exception.FarmDiaryNotFoundException;
import com.balirajahub.exception.FarmerProfileNotFoundException;
import com.balirajahub.repository.FarmDiaryRepository;
import com.balirajahub.repository.FarmerProfileRepository;
import com.balirajahub.service.AuthenticatedUserService;
import com.balirajahub.service.FarmDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmDiaryServiceImpl
        implements FarmDiaryService {

    private final FarmDiaryRepository farmDiaryRepository;

    private final FarmerProfileRepository farmerProfileRepository;

    private final AuthenticatedUserService authenticatedUserService;

    // ==========================================
    // Create Diary Entry
    // ==========================================

    @Override
    public FarmDiaryResponse createDiaryEntry(
            FarmDiaryRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        FarmDiary diary = FarmDiary.builder()

                .title(request.getTitle())

                .description(request.getDescription())

                .entryDate(request.getEntryDate())

                .activityType(request.getActivityType())

                .farmerProfile(farmerProfile)

                .build();

        return mapToResponse(
                farmDiaryRepository.save(diary));
    }

    // ==========================================
    // Get My Diary Entries
    // ==========================================

    @Override
    public List<FarmDiaryResponse> getMyDiaryEntries() {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        return farmDiaryRepository

                .findByFarmerProfileOrderByEntryDateDesc(
                        farmerProfile)

                .stream()

                .map(this::mapToResponse)

                .toList();
    }

    // ==========================================
    // Get Diary Entry By Id
    // ==========================================

    @Override
    public FarmDiaryResponse getDiaryEntryById(
            Long diaryId) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        FarmDiary diary = farmDiaryRepository

                .findByIdAndFarmerProfile(
                        diaryId,
                        farmerProfile)

                .orElseThrow(() ->
                        new FarmDiaryNotFoundException(
                                "Diary entry not found."));

        return mapToResponse(diary);
    }

    // ==========================================
    // Update Diary Entry
    // ==========================================

    @Override
    public FarmDiaryResponse updateDiaryEntry(
            Long diaryId,
            FarmDiaryRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        FarmDiary diary = farmDiaryRepository

                .findByIdAndFarmerProfile(
                        diaryId,
                        farmerProfile)

                .orElseThrow(() ->
                        new FarmDiaryNotFoundException(
                                "Diary entry not found."));

        diary.setTitle(request.getTitle());

        diary.setDescription(request.getDescription());

        diary.setEntryDate(request.getEntryDate());

        diary.setActivityType(request.getActivityType());

        return mapToResponse(
                farmDiaryRepository.save(diary));
    }

    // ==========================================
    // Delete Diary Entry
    // ==========================================

    @Override
    public void deleteDiaryEntry(
            Long diaryId) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        FarmDiary diary = farmDiaryRepository

                .findByIdAndFarmerProfile(
                        diaryId,
                        farmerProfile)

                .orElseThrow(() ->
                        new FarmDiaryNotFoundException(
                                "Diary entry not found."));

        farmDiaryRepository.delete(diary);
    }

    // ==========================================
    // Mapper
    // ==========================================

    private FarmDiaryResponse mapToResponse(
            FarmDiary diary) {

        return FarmDiaryResponse.builder()

                .id(diary.getId())

                .title(diary.getTitle())

                .description(diary.getDescription())

                .entryDate(diary.getEntryDate())

                .activityType(diary.getActivityType())

                .createdAt(diary.getCreatedAt())

                .updatedAt(diary.getUpdatedAt())

                .build();
    }
}