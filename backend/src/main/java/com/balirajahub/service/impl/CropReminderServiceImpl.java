package com.balirajahub.service.impl;

import com.balirajahub.dto.request.CropReminderRequest;
import com.balirajahub.dto.response.CropReminderResponse;
import com.balirajahub.entity.Crop;
import com.balirajahub.entity.CropReminder;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.User;
import com.balirajahub.entity.enums.ReminderStatus;
import com.balirajahub.exception.CropNotFoundException;
import com.balirajahub.exception.CropReminderNotFoundException;
import com.balirajahub.exception.FarmerProfileNotFoundException;
import com.balirajahub.repository.CropReminderRepository;
import com.balirajahub.repository.CropRepository;
import com.balirajahub.repository.FarmerProfileRepository;
import com.balirajahub.service.AuthenticatedUserService;
import com.balirajahub.service.CropReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CropReminderServiceImpl
        implements CropReminderService {

    private final CropReminderRepository cropReminderRepository;

    private final CropRepository cropRepository;

    private final FarmerProfileRepository farmerProfileRepository;

    private final AuthenticatedUserService authenticatedUserService;

    // ==========================================
    // Create Reminder
    // ==========================================

    @Override
    public CropReminderResponse createReminder(
            CropReminderRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        Crop crop = cropRepository
                .findByIdAndFarmerProfile(
                        request.getCropId(),
                        farmerProfile)
                .orElseThrow(() ->
                        new CropNotFoundException(
                                "Crop not found."));

        CropReminder reminder = CropReminder.builder()

                .title(request.getTitle())

                .description(request.getDescription())

                .activityDate(request.getActivityDate())

                .reminderDate(request.getReminderDate())

                .status(ReminderStatus.PENDING)

                .crop(crop)

                .farmerProfile(farmerProfile)

                .build();

        return mapToResponse(
                cropReminderRepository.save(reminder));
    }

    // ==========================================
    // Get My Reminders
    // ==========================================

    @Override
    public List<CropReminderResponse> getMyReminders() {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        return cropReminderRepository

                .findByFarmerProfileOrderByReminderDateAsc(
                        farmerProfile)

                .stream()

                .map(this::mapToResponse)

                .toList();
    }

    // ==========================================
    // Get Reminder By Id
    // ==========================================

    @Override
    public CropReminderResponse getReminderById(
            Long reminderId) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        CropReminder reminder = cropReminderRepository

                .findByIdAndFarmerProfile(
                        reminderId,
                        farmerProfile)

                .orElseThrow(() ->
                        new CropReminderNotFoundException(
                                "Reminder not found."));

        return mapToResponse(reminder);
    }

    // ==========================================
    // Update Reminder
    // ==========================================

    @Override
    public CropReminderResponse updateReminder(
            Long reminderId,
            CropReminderRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        CropReminder reminder = cropReminderRepository

                .findByIdAndFarmerProfile(
                        reminderId,
                        farmerProfile)

                .orElseThrow(() ->
                        new CropReminderNotFoundException(
                                "Reminder not found."));

        Crop crop = cropRepository
                .findByIdAndFarmerProfile(
                        request.getCropId(),
                        farmerProfile)
                .orElseThrow(() ->
                        new CropNotFoundException(
                                "Crop not found."));

        reminder.setTitle(request.getTitle());

        reminder.setDescription(request.getDescription());

        reminder.setActivityDate(request.getActivityDate());

        reminder.setReminderDate(request.getReminderDate());

        reminder.setCrop(crop);

        return mapToResponse(
                cropReminderRepository.save(reminder));
    }

    // ==========================================
    // Delete Reminder
    // ==========================================

    @Override
    public void deleteReminder(Long reminderId) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        CropReminder reminder = cropReminderRepository

                .findByIdAndFarmerProfile(
                        reminderId,
                        farmerProfile)

                .orElseThrow(() ->
                        new CropReminderNotFoundException(
                                "Reminder not found."));

        cropReminderRepository.delete(reminder);
    }

    // ==========================================
    // Mark Reminder As Completed
    // ==========================================

    @Override
    public CropReminderResponse markReminderAsCompleted(
            Long reminderId) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        CropReminder reminder = cropReminderRepository

                .findByIdAndFarmerProfile(
                        reminderId,
                        farmerProfile)

                .orElseThrow(() ->
                        new CropReminderNotFoundException(
                                "Reminder not found."));

        reminder.setStatus(ReminderStatus.COMPLETED);

        return mapToResponse(
                cropReminderRepository.save(reminder));
    }

    // ==========================================
    // Mapper
    // ==========================================

    private CropReminderResponse mapToResponse(
            CropReminder reminder) {

        return CropReminderResponse.builder()

                .id(reminder.getId())

                .title(reminder.getTitle())

                .description(reminder.getDescription())

                .activityDate(reminder.getActivityDate())

                .reminderDate(reminder.getReminderDate())

                .status(reminder.getStatus())

                .cropId(reminder.getCrop().getId())

                .cropName(reminder.getCrop().getCropName())

                .createdAt(reminder.getCreatedAt())

                .updatedAt(reminder.getUpdatedAt())

                .build();
    }
}
