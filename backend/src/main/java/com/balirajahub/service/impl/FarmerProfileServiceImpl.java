package com.balirajahub.service.impl;

import com.balirajahub.dto.request.FarmerProfileRequest;
import com.balirajahub.dto.response.FarmerProfileResponse;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.repository.FarmerProfileRepository;
import com.balirajahub.repository.UserRepository;
import com.balirajahub.service.FarmerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.balirajahub.entity.User;
import com.balirajahub.exception.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class FarmerProfileServiceImpl implements FarmerProfileService {

    private final FarmerProfileRepository farmerProfileRepository;
    private final UserRepository userRepository;

    @Override
    public FarmerProfileResponse createProfile(FarmerProfileRequest request) {

        User user = getCurrentUser();

        if (farmerProfileRepository.existsByUser(user)) {
            throw new IllegalStateException("Farmer profile already exists.");
        }

        FarmerProfile farmerProfile = FarmerProfile.builder()
                .user(user)
                .village(request.getVillage())
                .taluka(request.getTaluka())
                .district(request.getDistrict())
                .state(request.getState())
                .pinCode(request.getPinCode())
                .farmSize(request.getFarmSize())
                .build();

        farmerProfileRepository.save(farmerProfile);

        return mapToResponse(user, farmerProfile);
    }

    @Override
    public FarmerProfileResponse getProfile() {

        User user = getCurrentUser();

        FarmerProfile profile = farmerProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new IllegalStateException("Farmer profile not found."));

        return mapToResponse(user, profile);
    }

    @Override
    public FarmerProfileResponse updateProfile(FarmerProfileRequest request) {

        User user = getCurrentUser();

        FarmerProfile profile = farmerProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new IllegalStateException("Farmer profile not found."));

        profile.setVillage(request.getVillage());
        profile.setTaluka(request.getTaluka());
        profile.setDistrict(request.getDistrict());
        profile.setState(request.getState());
        profile.setPinCode(request.getPinCode());
        profile.setFarmSize(request.getFarmSize());

        farmerProfileRepository.save(profile);

        return mapToResponse(user, profile);
    }


    private FarmerProfileResponse mapToResponse(User user, FarmerProfile profile) {

        return FarmerProfileResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .village(profile.getVillage())
                .taluka(profile.getTaluka())
                .district(profile.getDistrict())
                .state(profile.getState())
                .pinCode(profile.getPinCode())
                .farmSize(profile.getFarmSize())
                .profileImage(profile.getProfileImage())
                .build();
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
    }
}