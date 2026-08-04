package com.balirajahub.service.impl;

import com.balirajahub.dto.response.DashboardSummaryResponse;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.User;
import com.balirajahub.entity.enums.CropStatus;
import com.balirajahub.entity.enums.ReminderStatus;
import com.balirajahub.exception.FarmerProfileNotFoundException;
import com.balirajahub.repository.CropRepository;
import com.balirajahub.repository.ExpenseRepository;
import com.balirajahub.repository.FarmDiaryRepository;
import com.balirajahub.repository.FarmerProfileRepository;
import com.balirajahub.service.AuthenticatedUserService;
import com.balirajahub.service.DashboardService;
import com.balirajahub.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.balirajahub.repository.CropReminderRepository;


import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl
        implements DashboardService {

    private final CropRepository cropRepository;

    private final FarmDiaryRepository farmDiaryRepository;

    private final ExpenseRepository expenseRepository;

    private final FarmerProfileRepository farmerProfileRepository;

    private final NotificationService notificationService;

    private final AuthenticatedUserService authenticatedUserService;

    private final CropReminderRepository cropReminderRepository;


    @Override
    public DashboardSummaryResponse getDashboardSummary() {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        LocalDate today = LocalDate.now();

        LocalDate nextThirtyDays = today.plusDays(30);

        return DashboardSummaryResponse.builder()

             .totalCrops(
                cropRepository.countByFarmerProfile(
                        farmerProfile))

                .activeCrops(
                        cropRepository.countByFarmerProfileAndStatus(
                                farmerProfile,
                                CropStatus.GROWING))

                .totalDiaryEntries(
                        farmDiaryRepository.countByFarmerProfile(
                                farmerProfile))

                .totalExpenses(
                        expenseRepository.countByFarmerProfile(
                                farmerProfile))

                .totalExpenseAmount(
                        expenseRepository.getTotalExpenseAmount(
                                farmerProfile))

                .unreadNotifications(
                        notificationService.getUnreadNotificationCount())

                .upcomingHarvests(
                        cropRepository
                                .countByFarmerProfileAndExpectedHarvestDateBetween(
                                        farmerProfile,
                                        today,
                                        nextThirtyDays))

                // ✅ ADD THIS
                .pendingReminders(
                        cropReminderRepository.countByFarmerProfileAndStatus(
                                farmerProfile,
                                ReminderStatus.PENDING))

                .build();
    }
}
