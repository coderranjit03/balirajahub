package com.balirajahub.service.impl;

import com.balirajahub.dto.request.FarmerProfileRequest;
import com.balirajahub.dto.response.FarmerProfileResponse;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.exception.*;
import com.balirajahub.repository.FarmerProfileRepository;
import com.balirajahub.repository.UserRepository;
import com.balirajahub.service.AuthenticatedUserService;
import com.balirajahub.service.FarmerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.balirajahub.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FarmerProfileServiceImpl implements FarmerProfileService {

    private final FarmerProfileRepository farmerProfileRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public FarmerProfileResponse createProfile(FarmerProfileRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        if (farmerProfileRepository.existsByUser(user)) {
            throw new FarmerProfileAlreadyExistsException(
                    "Farmer profile already exists."
            );
        }

        FarmerProfile farmerProfile = FarmerProfile.builder()
                .user(user)
                .village(request.getVillage())
                .taluka(request.getTaluka())
                .district(request.getDistrict())
                .state(request.getState())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .pinCode(request.getPinCode())
                .farmSize(request.getFarmSize())
                .build();

        farmerProfileRepository.save(farmerProfile);

        return mapToResponse(user, farmerProfile);
    }

    @Override
    public FarmerProfileResponse getProfile() {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile profile = farmerProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."
                        ));

        return mapToResponse(user, profile);
    }

    @Override
    public FarmerProfileResponse updateProfile(FarmerProfileRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile profile = farmerProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."
                        ));

        profile.setVillage(request.getVillage());
        profile.setTaluka(request.getTaluka());
        profile.setDistrict(request.getDistrict());
        profile.setState(request.getState());
        profile.setLatitude(request.getLatitude());
        profile.setLongitude(request.getLongitude());
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
                .latitude(profile.getLatitude())
                .longitude(profile.getLongitude())
                .pinCode(profile.getPinCode())
                .farmSize(profile.getFarmSize())
                .profileImage(profile.getProfileImage())
                .build();
    }


    @Override
    public FarmerProfileResponse uploadProfileImage(MultipartFile image) {

        if (image.isEmpty()) {
            throw new InvalidFileException(
                    "Please select an image.");
        }

        String contentType = image.getContentType();

        if (contentType == null ||
                !(contentType.equals("image/jpeg")
                        || contentType.equals("image/png"))) {

            throw new InvalidFileException(
                    "Only JPG and PNG images are allowed.");
        }

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile profile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        try {

            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + image.getOriginalFilename();

            Path uploadPath = Paths.get("uploads/profile-images");

            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);

            System.out.println("Saving File To: " + filePath.toAbsolutePath());

            image.transferTo(filePath);


            profile.setProfileImage(
                    "/uploads/profile-images/" + fileName);

            farmerProfileRepository.save(profile);

            return mapToResponse(user, profile);

        } catch (IOException e) {

            e.printStackTrace();

            throw new ImageUploadException(
                    "Failed to upload image.",
                    e);
        }
    }
}