package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.response.DashboardSummaryResponse;
import com.balirajahub.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ApiResponse<DashboardSummaryResponse> getDashboardSummary() {

        return ApiResponse.success(
                "Dashboard summary fetched successfully.",
                dashboardService.getDashboardSummary()
        );
    }
}
