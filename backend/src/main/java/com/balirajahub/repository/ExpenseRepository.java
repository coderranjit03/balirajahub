package com.balirajahub.repository;

import com.balirajahub.entity.Crop;
import com.balirajahub.entity.Expense;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.enums.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    @Query("""
    SELECT COALESCE(SUM(e.amount), 0)
    FROM Expense e
    WHERE e.farmerProfile = :farmerProfile
    """)
    BigDecimal getTotalExpenseAmount(
            @Param("farmerProfile")
            FarmerProfile farmerProfile);

    List<Expense> findByFarmerProfileOrderByExpenseDateDesc(
            FarmerProfile farmerProfile
    );

    Optional<Expense> findByIdAndFarmerProfile(
            Long id,
            FarmerProfile farmerProfile
    );

    List<Expense> findByFarmerProfileAndExpenseDate(
            FarmerProfile farmerProfile,
            LocalDate expenseDate
    );

    List<Expense> findByFarmerProfileAndCategory(
            FarmerProfile farmerProfile,
            ExpenseCategory category
    );

    List<Expense> findByFarmerProfileAndCrop(
            FarmerProfile farmerProfile,
            Crop crop
    );

    long countByFarmerProfile(FarmerProfile farmerProfile);

}