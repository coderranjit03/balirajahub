package com.balirajahub.service;

import com.balirajahub.dto.request.CropReminderRequest;
import com.balirajahub.dto.response.CropReminderResponse;

import java.util.List;

public interface CropReminderService {

    CropReminderResponse createReminder(
            CropReminderRequest request);

    List<CropReminderResponse> getMyReminders();

    CropReminderResponse getReminderById(
            Long reminderId);

    CropReminderResponse updateReminder(
            Long reminderId,
            CropReminderRequest request);

    void deleteReminder(
            Long reminderId);

    CropReminderResponse markReminderAsCompleted(
            Long reminderId);
}
