package com.balirajahub.service;

import com.balirajahub.dto.request.FarmerProfileRequest;
import com.balirajahub.dto.response.FarmerProfileResponse;
import com.balirajahub.entity.FarmerProfile;
import org.springframework.web.multipart.MultipartFile;

public interface FarmerProfileService {

    FarmerProfileResponse createProfile(FarmerProfileRequest request);

    FarmerProfileResponse getProfile();

    FarmerProfileResponse updateProfile(FarmerProfileRequest request);

    FarmerProfileResponse uploadProfileImage(MultipartFile image);

    FarmerProfile getCurrentFarmerProfile();
}