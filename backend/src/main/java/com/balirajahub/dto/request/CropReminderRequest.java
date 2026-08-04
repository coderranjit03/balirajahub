package com.balirajahub.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropReminderRequest {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotNull(message = "Activity date is required.")
    @FutureOrPresent(message = "Activity date cannot be in the past.")
    private LocalDate activityDate;

    @NotNull(message = "Reminder date is required.")
    private LocalDate reminderDate;

    // Optional
    private Long cropId;
}