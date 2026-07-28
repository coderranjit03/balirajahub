package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.request.ExpenseRequest;
import com.balirajahub.dto.response.ExpenseResponse;
import com.balirajahub.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    // ==========================================
    // Create Expense
    // ==========================================

    @PostMapping
    public ApiResponse<ExpenseResponse> createExpense(
            @Valid @RequestBody ExpenseRequest request) {

        return ApiResponse.success(
                "Expense created successfully.",
                expenseService.createExpense(request)
        );
    }

    // ==========================================
    // Get My Expenses
    // ==========================================

    @GetMapping
    public ApiResponse<List<ExpenseResponse>> getMyExpenses() {

        return ApiResponse.success(
                "Expenses fetched successfully.",
                expenseService.getMyExpenses()
        );
    }

    // ==========================================
    // Get Expense By Id
    // ==========================================

    @GetMapping("/{expenseId}")
    public ApiResponse<ExpenseResponse> getExpenseById(
            @PathVariable Long expenseId) {

        return ApiResponse.success(
                "Expense fetched successfully.",
                expenseService.getExpenseById(expenseId)
        );
    }

    // ==========================================
    // Update Expense
    // ==========================================

    @PutMapping("/{expenseId}")
    public ApiResponse<ExpenseResponse> updateExpense(
            @PathVariable Long expenseId,
            @Valid @RequestBody ExpenseRequest request) {

        return ApiResponse.success(
                "Expense updated successfully.",
                expenseService.updateExpense(expenseId, request)
        );
    }

    // ==========================================
    // Delete Expense
    // ==========================================

    @DeleteMapping("/{expenseId}")
    public ApiResponse<Void> deleteExpense(
            @PathVariable Long expenseId) {

        expenseService.deleteExpense(expenseId);

        return ApiResponse.success(
                "Expense deleted successfully.",
                null
        );
    }
}
