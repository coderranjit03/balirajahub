package com.balirajahub.service.impl;

import com.balirajahub.dto.request.CropRequest;
import com.balirajahub.dto.response.CropResponse;
import com.balirajahub.entity.Crop;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.User;
import com.balirajahub.entity.enums.CropStatus;
import com.balirajahub.exception.CropNotFoundException;
import com.balirajahub.exception.FarmerProfileNotFoundException;
import com.balirajahub.exception.UserNotFoundException;
import com.balirajahub.repository.CropRepository;
import com.balirajahub.repository.FarmerProfileRepository;
import com.balirajahub.repository.UserRepository;
import com.balirajahub.service.AuthenticatedUserService;
import com.balirajahub.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {

    private final CropRepository cropRepository;

    private final FarmerProfileRepository farmerProfileRepository;

    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public CropResponse createCrop(CropRequest request) {

        FarmerProfile farmerProfile = getCurrentFarmerProfile();

        if (request.getExpectedHarvestDate()
                .isBefore(request.getSowingDate())) {

            throw new IllegalArgumentException(
                    "Expected harvest date cannot be before sowing date.");
        }

        Crop crop = Crop.builder()
                .cropName(request.getCropName())
                .season(request.getSeason())
                .area(request.getArea())
                .sowingDate(request.getSowingDate())
                .expectedHarvestDate(request.getExpectedHarvestDate())
                .status(CropStatus.PLANNED)
                .farmerProfile(farmerProfile)
                .build();

        Crop savedCrop = cropRepository.save(crop);

        return mapToResponse(savedCrop);
    }

    @Override
    public List<CropResponse> getAllCrops() {

        FarmerProfile farmerProfile = getCurrentFarmerProfile();

        return cropRepository
                .findByFarmerProfile(farmerProfile)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CropResponse updateCrop(Long id, CropRequest request) {

        FarmerProfile farmerProfile = getCurrentFarmerProfile();

        Crop crop = cropRepository
                .findByIdAndFarmerProfile(id, farmerProfile)
                .orElseThrow(() ->
                        new CropNotFoundException(
                                "Crop not found."));

        if (request.getExpectedHarvestDate()
                .isBefore(request.getSowingDate())) {

            throw new IllegalArgumentException(
                    "Expected harvest date cannot be before sowing date.");
        }

        crop.setCropName(request.getCropName());
        crop.setSeason(request.getSeason());
        crop.setArea(request.getArea());
        crop.setSowingDate(request.getSowingDate());
        crop.setExpectedHarvestDate(request.getExpectedHarvestDate());

        Crop updatedCrop = cropRepository.save(crop);

        return mapToResponse(updatedCrop);
    }

    @Override
    public void deleteCrop(Long id) {

        FarmerProfile farmerProfile = getCurrentFarmerProfile();

        Crop crop = cropRepository
                .findByIdAndFarmerProfile(id, farmerProfile)
                .orElseThrow(() ->
                        new CropNotFoundException(
                                "Crop not found."));

        cropRepository.delete(crop);
    }

    @Override
    public CropResponse getCropById(Long id) {

        FarmerProfile farmerProfile = getCurrentFarmerProfile();

        Crop crop =
                cropRepository
                        .findByIdAndFarmerProfile(
                                id,
                                farmerProfile)
                        .orElseThrow(() ->
                                new CropNotFoundException(
                                        "Crop not found."));

        return mapToResponse(crop);
    }

    private CropResponse mapToResponse(Crop crop) {

        return CropResponse.builder()
                .id(crop.getId())
                .cropName(crop.getCropName())
                .season(crop.getSeason())
                .area(crop.getArea())
                .sowingDate(crop.getSowingDate())
                .expectedHarvestDate(crop.getExpectedHarvestDate())
                .status(crop.getStatus())
                .build();
    }


    private FarmerProfile getCurrentFarmerProfile() {

        User user = authenticatedUserService.getCurrentUser();

        return farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));
    }
}