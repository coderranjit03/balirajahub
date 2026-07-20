package com.balirajahub.dto.request;

import com.balirajahub.entity.enums.Season;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropRequest {

    @NotBlank(message = "Crop name is required.")
    private String cropName;

    @NotNull(message = "Season is required.")
    private Season season;

    @NotNull(message = "Area is required.")
    @Positive(message = "Area must be greater than zero.")
    private Double area;

    @NotNull(message = "Sowing date is required.")
    private LocalDate sowingDate;

    @NotNull(message = "Expected harvest date is required.")
    @Future(message = "Harvest date must be in the future.")
    private LocalDate expectedHarvestDate;
}