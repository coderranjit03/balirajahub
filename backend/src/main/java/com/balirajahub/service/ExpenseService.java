package com.balirajahub.service;

import com.balirajahub.dto.request.ExpenseRequest;
import com.balirajahub.dto.response.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    ExpenseResponse createExpense(
            ExpenseRequest request);

    List<ExpenseResponse> getMyExpenses();

    ExpenseResponse getExpenseById(
            Long expenseId);

    ExpenseResponse updateExpense(
            Long expenseId,
            ExpenseRequest request);

    void deleteExpense(
            Long expenseId);

}