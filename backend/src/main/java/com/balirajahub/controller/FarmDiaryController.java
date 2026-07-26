package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.request.FarmDiaryRequest;
import com.balirajahub.dto.response.FarmDiaryResponse;
import com.balirajahub.service.FarmDiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farm-diary")
@RequiredArgsConstructor
public class FarmDiaryController {

    private final FarmDiaryService farmDiaryService;

    // ==========================================
    // Create Diary Entry
    // ==========================================

    @PostMapping
    public ApiResponse<FarmDiaryResponse> createDiaryEntry(
            @Valid @RequestBody FarmDiaryRequest request) {

        return ApiResponse.success(
                "Diary entry created successfully.",
                farmDiaryService.createDiaryEntry(request)
        );
    }

    // ==========================================
    // Get All Diary Entries
    // ==========================================

    @GetMapping
    public ApiResponse<List<FarmDiaryResponse>> getMyDiaryEntries() {

        return ApiResponse.success(
                "Diary entries fetched successfully.",
                farmDiaryService.getMyDiaryEntries()
        );
    }

    // ==========================================
    // Get Diary Entry By Id
    // ==========================================

    @GetMapping("/{diaryId}")
    public ApiResponse<FarmDiaryResponse> getDiaryEntryById(
            @PathVariable Long diaryId) {

        return ApiResponse.success(
                "Diary entry fetched successfully.",
                farmDiaryService.getDiaryEntryById(diaryId)
        );
    }

    // ==========================================
    // Update Diary Entry
    // ==========================================

    @PutMapping("/{diaryId}")
    public ApiResponse<FarmDiaryResponse> updateDiaryEntry(
            @PathVariable Long diaryId,
            @Valid @RequestBody FarmDiaryRequest request) {

        return ApiResponse.success(
                "Diary entry updated successfully.",
                farmDiaryService.updateDiaryEntry(diaryId, request)
        );
    }

    // ==========================================
    // Delete Diary Entry
    // ==========================================

    @DeleteMapping("/{diaryId}")
    public ApiResponse<Void> deleteDiaryEntry(
            @PathVariable Long diaryId) {

        farmDiaryService.deleteDiaryEntry(diaryId);

        return ApiResponse.success(
                "Diary entry deleted successfully.",
                null
        );
    }
}