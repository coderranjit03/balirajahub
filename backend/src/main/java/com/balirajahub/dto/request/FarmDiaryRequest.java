package com.balirajahub.dto.request;

import com.balirajahub.entity.enums.DiaryActivityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmDiaryRequest {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotNull(message = "Entry date is required.")
    private LocalDate entryDate;

    @NotNull(message = "Activity type is required.")
    private DiaryActivityType activityType;

}