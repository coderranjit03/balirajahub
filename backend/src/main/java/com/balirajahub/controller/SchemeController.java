package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.request.SchemeSearchRequest;
import com.balirajahub.dto.response.SchemeResponse;
import com.balirajahub.service.SchemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schemes")
@RequiredArgsConstructor
public class SchemeController {

    private final SchemeService schemeService;

    // ==========================================
    // Search / Filter / Pagination / Sorting
    // ==========================================

    @PostMapping("/")
    public ApiResponse<Page<SchemeResponse>> searchSchemes(
            @RequestBody SchemeSearchRequest request) {

        return ApiResponse.success(
                "Schemes fetched successfully.",
                schemeService.searchSchemes(request)
        );
    }

    // ==========================================
    // Get Scheme By Id
    // ==========================================

    @GetMapping("/{id}")
    public ApiResponse<SchemeResponse> getSchemeById(
            @PathVariable Long id) {

        return ApiResponse.success(
                "Scheme fetched successfully.",
                schemeService.getSchemeById(id)
        );
    }
}