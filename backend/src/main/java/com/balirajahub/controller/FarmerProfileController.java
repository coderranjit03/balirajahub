package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.request.FarmerProfileRequest;
import com.balirajahub.dto.response.FarmerProfileResponse;
import com.balirajahub.service.FarmerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/farmer/profile")
@RequiredArgsConstructor
public class FarmerProfileController {

    private final FarmerProfileService farmerProfileService;

    @PostMapping
    public ApiResponse<FarmerProfileResponse> createProfile(
            @Valid @RequestBody FarmerProfileRequest request) {

        FarmerProfileResponse response =
                farmerProfileService.createProfile(request);

        return ApiResponse.success(
                "Farmer profile created successfully.",
                response
        );
    }

    @GetMapping
    public ApiResponse<FarmerProfileResponse> getProfile() {

        FarmerProfileResponse response =
                farmerProfileService.getProfile();

        return ApiResponse.success(
                "Farmer profile fetched successfully.",
                response
        );
    }

    @PutMapping
    public ApiResponse<FarmerProfileResponse> updateProfile(
            @Valid @RequestBody FarmerProfileRequest request) {

        FarmerProfileResponse response =
                farmerProfileService.updateProfile(request);

        return ApiResponse.success(
                "Farmer profile updated successfully.",
                response
        );
    }

    @PostMapping("/image")
    public ApiResponse<FarmerProfileResponse> uploadProfileImage(
            @RequestParam("image") MultipartFile image) {

        FarmerProfileResponse response =
                farmerProfileService.uploadProfileImage(image);

        return ApiResponse.success(
                "Profile image uploaded successfully.",
                response
        );
    }
}