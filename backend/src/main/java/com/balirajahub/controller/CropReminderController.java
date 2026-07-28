package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.request.CropReminderRequest;
import com.balirajahub.dto.response.CropReminderResponse;
import com.balirajahub.service.CropReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor
public class CropReminderController {

    private final CropReminderService cropReminderService;

    // ==========================================
    // Create Reminder
    // ==========================================

    @PostMapping
    public ApiResponse<CropReminderResponse> createReminder(
            @Valid @RequestBody CropReminderRequest request) {

        return ApiResponse.success(
                "Reminder created successfully.",
                cropReminderService.createReminder(request)
        );
    }

    // ==========================================
    // Get My Reminders
    // ==========================================

    @GetMapping
    public ApiResponse<List<CropReminderResponse>> getMyReminders() {

        return ApiResponse.success(
                "Reminders fetched successfully.",
                cropReminderService.getMyReminders()
        );
    }

    // ==========================================
    // Get Reminder By Id
    // ==========================================

    @GetMapping("/{reminderId}")
    public ApiResponse<CropReminderResponse> getReminderById(
            @PathVariable Long reminderId) {

        return ApiResponse.success(
                "Reminder fetched successfully.",
                cropReminderService.getReminderById(reminderId)
        );
    }

    // ==========================================
    // Update Reminder
    // ==========================================

    @PutMapping("/{reminderId}")
    public ApiResponse<CropReminderResponse> updateReminder(
            @PathVariable Long reminderId,
            @Valid @RequestBody CropReminderRequest request) {

        return ApiResponse.success(
                "Reminder updated successfully.",
                cropReminderService.updateReminder(reminderId, request)
        );
    }

    // ==========================================
    // Mark Reminder As Completed
    // ==========================================

    @PatchMapping("/{reminderId}/complete")
    public ApiResponse<CropReminderResponse> markReminderAsCompleted(
            @PathVariable Long reminderId) {

        return ApiResponse.success(
                "Reminder marked as completed.",
                cropReminderService.markReminderAsCompleted(reminderId)
        );
    }

    // ==========================================
    // Delete Reminder
    // ==========================================

    @DeleteMapping("/{reminderId}")
    public ApiResponse<Void> deleteReminder(
            @PathVariable Long reminderId) {

        cropReminderService.deleteReminder(reminderId);

        return ApiResponse.success(
                "Reminder deleted successfully.",
                null
        );
    }
}
