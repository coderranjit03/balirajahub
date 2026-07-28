package com.balirajahub.service.impl;

import com.balirajahub.dto.request.ExpenseRequest;
import com.balirajahub.dto.response.ExpenseResponse;
import com.balirajahub.entity.Crop;
import com.balirajahub.entity.Expense;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.User;
import com.balirajahub.exception.CropNotFoundException;
import com.balirajahub.exception.ExpenseNotFoundException;
import com.balirajahub.exception.FarmerProfileNotFoundException;
import com.balirajahub.repository.CropRepository;
import com.balirajahub.repository.ExpenseRepository;
import com.balirajahub.repository.FarmerProfileRepository;
import com.balirajahub.service.AuthenticatedUserService;
import com.balirajahub.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final FarmerProfileRepository farmerProfileRepository;

    private final CropRepository cropRepository;

    private final AuthenticatedUserService authenticatedUserService;

    // ==========================================
    // Create Expense
    // ==========================================

    @Override
    public ExpenseResponse createExpense(ExpenseRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        Crop crop = null;

        if (request.getCropId() != null) {

            crop = cropRepository
                    .findByIdAndFarmerProfile(
                            request.getCropId(),
                            farmerProfile)
                    .orElseThrow(() ->
                            new CropNotFoundException(
                                    "Crop not found."));
        }

        Expense expense = Expense.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .expenseDate(request.getExpenseDate())
                .category(request.getCategory())
                .crop(crop)
                .farmerProfile(farmerProfile)
                .build();

        return mapToResponse(
                expenseRepository.save(expense));
    }

    // ==========================================
    // Get My Expenses
    // ==========================================

    @Override
    public List<ExpenseResponse> getMyExpenses() {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        return expenseRepository
                .findByFarmerProfileOrderByExpenseDateDesc(
                        farmerProfile)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================================
    // Get Expense By Id
    // ==========================================

    @Override
    public ExpenseResponse getExpenseById(Long expenseId) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        Expense expense = expenseRepository
                .findByIdAndFarmerProfile(
                        expenseId,
                        farmerProfile)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found."));

        return mapToResponse(expense);
    }

    // ==========================================
    // Update Expense
    // ==========================================

    @Override
    public ExpenseResponse updateExpense(
            Long expenseId,
            ExpenseRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        Expense expense = expenseRepository
                .findByIdAndFarmerProfile(
                        expenseId,
                        farmerProfile)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found."));

        Crop crop = null;

        if (request.getCropId() != null) {

            crop = cropRepository
                    .findByIdAndFarmerProfile(
                            request.getCropId(),
                            farmerProfile)
                    .orElseThrow(() ->
                            new CropNotFoundException(
                                    "Crop not found."));
        }

        expense.setTitle(request.getTitle());

        expense.setDescription(request.getDescription());

        expense.setAmount(request.getAmount());

        expense.setExpenseDate(request.getExpenseDate());

        expense.setCategory(request.getCategory());

        expense.setCrop(crop);

        return mapToResponse(
                expenseRepository.save(expense));
    }

    // ==========================================
    // Delete Expense
    // ==========================================

    @Override
    public void deleteExpense(Long expenseId) {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile = farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));

        Expense expense = expenseRepository
                .findByIdAndFarmerProfile(
                        expenseId,
                        farmerProfile)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found."));

        expenseRepository.delete(expense);
    }

    // ==========================================
    // Mapper
    // ==========================================

    private ExpenseResponse mapToResponse(
            Expense expense) {

        return ExpenseResponse.builder()

                .id(expense.getId())

                .title(expense.getTitle())

                .description(expense.getDescription())

                .amount(expense.getAmount())

                .expenseDate(expense.getExpenseDate())

                .category(expense.getCategory())

                .cropId(
                        expense.getCrop() != null
                                ? expense.getCrop().getId()
                                : null)

                .cropName(
                        expense.getCrop() != null
                                ? expense.getCrop().getCropName()
                                : null)

                .createdAt(expense.getCreatedAt())

                .updatedAt(expense.getUpdatedAt())

                .build();
    }
}