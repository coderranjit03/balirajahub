package com.balirajahub.dto.request;

import com.balirajahub.entity.enums.ExpenseCategory;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequest {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotNull(message = "Amount is required.")
    @DecimalMin(
            value = "0.01",
            message = "Amount must be greater than 0."
    )
    private BigDecimal amount;

    @NotNull(message = "Expense date is required.")
    private LocalDate expenseDate;

    @NotNull(message = "Expense category is required.")
    private ExpenseCategory category;

    // Optional
    private Long cropId;

}