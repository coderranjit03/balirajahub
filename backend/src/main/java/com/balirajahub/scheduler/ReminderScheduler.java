package com.balirajahub.scheduler;

import com.balirajahub.entity.CropReminder;
import com.balirajahub.entity.enums.NotificationType;
import com.balirajahub.entity.enums.ReminderStatus;
import com.balirajahub.repository.CropReminderRepository;
import com.balirajahub.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReminderScheduler {

    private final CropReminderRepository cropReminderRepository;

    private final NotificationService notificationService;

    // Runs every day at 8:00 AM
    @Scheduled(cron = "0 0 8 * * *")
    public void sendDailyReminders() {

        LocalDate today = LocalDate.now();

        List<CropReminder> reminders =
                cropReminderRepository.findByReminderDateAndStatus(
                        today,
                        ReminderStatus.PENDING
                );

        for (CropReminder reminder : reminders) {

            String title = "Crop Reminder: "
                    + reminder.getTitle();

            String message =
                    reminder.getDescription()
                            + " for crop "
                            + reminder.getCrop().getCropName()
                            + " scheduled on "
                            + reminder.getActivityDate();

            try {

                notificationService.createNotification(
                        title,
                        message,
                        NotificationType.REMINDER
                );

                log.info(
                        "Reminder notification created for reminder id {}",
                        reminder.getId());

            } catch (Exception ex) {

                log.error(
                        "Failed to create reminder notification for reminder id {}",
                        reminder.getId(),
                        ex);
            }
        }
    }
}
