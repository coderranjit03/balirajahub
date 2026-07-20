package com.balirajahub.service;

import com.balirajahub.dto.request.CropRequest;
import com.balirajahub.dto.response.CropResponse;

import java.util.List;

public interface CropService {

    CropResponse createCrop(CropRequest request);
    List<CropResponse> getAllCrops();
    CropResponse updateCrop(Long id, CropRequest request);
    void deleteCrop(Long id);
    CropResponse getCropById(Long id);

}