package com.balirajahub.repository;

import com.balirajahub.entity.Crop;
import com.balirajahub.entity.CropReminder;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.enums.ReminderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CropReminderRepository
        extends JpaRepository<CropReminder, Long> {

    // ==========================================
    // Get all reminders of a farmer
    // ==========================================

    List<CropReminder> findByFarmerProfileOrderByReminderDateAsc(
            FarmerProfile farmerProfile
    );

    // ==========================================
    // Get reminder by id and owner
    // ==========================================

    Optional<CropReminder> findByIdAndFarmerProfile(
            Long id,
            FarmerProfile farmerProfile
    );

    // ==========================================
    // Get reminders of a specific crop
    // ==========================================

    List<CropReminder> findByFarmerProfileAndCropOrderByReminderDateAsc(
            FarmerProfile farmerProfile,
            Crop crop
    );

    // ==========================================
    // Get reminders by status
    // ==========================================

    List<CropReminder> findByFarmerProfileAndStatusOrderByReminderDateAsc(
            FarmerProfile farmerProfile,
            ReminderStatus status
    );

    // ==========================================
    // Get reminders for today
    // ==========================================

    List<CropReminder> findByFarmerProfileAndReminderDate(
            FarmerProfile farmerProfile,
            LocalDate reminderDate
    );

    // ==========================================
    // Scheduler support (all pending reminders)
    // ==========================================

    List<CropReminder> findByReminderDateAndStatus(
            LocalDate reminderDate,
            ReminderStatus status
    );

}
