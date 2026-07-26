package com.balirajahub.dto.response;

import com.balirajahub.entity.enums.DiaryActivityType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmDiaryResponse {

    private Long id;

    private String title;

    private String description;

    private LocalDate entryDate;

    private DiaryActivityType activityType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}