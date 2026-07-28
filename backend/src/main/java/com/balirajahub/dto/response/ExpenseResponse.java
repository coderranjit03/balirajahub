package com.balirajahub.dto.response;

import com.balirajahub.entity.enums.ExpenseCategory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponse {

    private Long id;

    private String title;

    private String description;

    private BigDecimal amount;

    private LocalDate expenseDate;

    private ExpenseCategory category;

    private Long cropId;

    private String cropName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}