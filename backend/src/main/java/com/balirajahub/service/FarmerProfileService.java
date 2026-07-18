package com.balirajahub.service;

import com.balirajahub.dto.request.FarmerProfileRequest;
import com.balirajahub.dto.response.FarmerProfileResponse;

public interface FarmerProfileService {

    FarmerProfileResponse createProfile(FarmerProfileRequest request);

    FarmerProfileResponse getProfile();

    FarmerProfileResponse updateProfile(FarmerProfileRequest request);
}