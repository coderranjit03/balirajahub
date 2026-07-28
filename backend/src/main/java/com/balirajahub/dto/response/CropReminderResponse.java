package com.balirajahub.dto.response;

import com.balirajahub.entity.enums.ReminderStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropReminderResponse {

    private Long id;

    private String title;

    private String description;

    private LocalDate activityDate;

    private LocalDate reminderDate;

    private ReminderStatus status;

    private Long cropId;

    private String cropName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
