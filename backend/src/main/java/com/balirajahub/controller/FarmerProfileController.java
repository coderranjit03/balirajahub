package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.request.FarmerProfileRequest;
import com.balirajahub.dto.response.FarmerProfileResponse;
import com.balirajahub.entity.User;
import com.balirajahub.repository.FarmerProfileRepository;
import com.balirajahub.repository.UserRepository;
import com.balirajahub.service.FarmerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/farmer/profile")
@RequiredArgsConstructor
public class FarmerProfileController {

    private final FarmerProfileService farmerProfileService;
    private final FarmerProfileRepository farmerProfileRepository;
    private final UserRepository userRepository;

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


    @GetMapping("/me/status")
    public ApiResponse<Boolean> hasProfile(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean exists = farmerProfileRepository.existsByUser(user);

        return ApiResponse.success(
                "Profile status fetched successfully.",
                exists
        );
    }

}