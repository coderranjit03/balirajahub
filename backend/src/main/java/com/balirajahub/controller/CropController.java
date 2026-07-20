package com.balirajahub.controller;

import com.balirajahub.dto.request.CropRequest;
import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.response.CropResponse;
import com.balirajahub.service.CropService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmer/crops")
@RequiredArgsConstructor
public class CropController {

    private final CropService cropService;

    @PostMapping
    public ApiResponse<CropResponse> createCrop(
            @Valid @RequestBody CropRequest request) {

        CropResponse response = cropService.createCrop(request);

        return ApiResponse.success(
                "Crop created successfully.",
                response
        );
    }

    @GetMapping
    public ApiResponse<List<CropResponse>> getAllCrops() {

        return ApiResponse.success(
                "Crops fetched successfully.",
                cropService.getAllCrops()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<CropResponse> getCropById(
            @PathVariable Long id) {

        return ApiResponse.success(
                "Crop fetched successfully.",
                cropService.getCropById(id)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<CropResponse> updateCrop(
            @PathVariable Long id,
            @Valid @RequestBody CropRequest request) {

        return ApiResponse.success(
                "Crop updated successfully.",
                cropService.updateCrop(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCrop(
            @PathVariable Long id) {

        cropService.deleteCrop(id);

        return ApiResponse.success(
                "Crop deleted successfully."
        );
    }
}