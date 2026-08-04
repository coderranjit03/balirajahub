package com.balirajahub.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryResponse {

    private Long totalCrops;

    private Long activeCrops;

    private Long totalDiaryEntries;

    private Long totalExpenses;

    private BigDecimal totalExpenseAmount;

    private Long unreadNotifications;

    private Long upcomingHarvests;

    // ✅ ADD THIS
    private Long pendingReminders;

}
