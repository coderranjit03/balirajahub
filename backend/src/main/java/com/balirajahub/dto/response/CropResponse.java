package com.balirajahub.dto.response;

import com.balirajahub.entity.enums.CropStatus;
import com.balirajahub.entity.enums.Season;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropResponse {

    private Long id;

    private String cropName;

    private Season season;

    private Double area;

    private LocalDate sowingDate;

    private LocalDate expectedHarvestDate;

    private CropStatus status;
}