package com.balirajahub.service;

import com.balirajahub.dto.request.FarmDiaryRequest;
import com.balirajahub.dto.response.FarmDiaryResponse;

import java.util.List;

public interface FarmDiaryService {

    FarmDiaryResponse createDiaryEntry(
            FarmDiaryRequest request);

    List<FarmDiaryResponse> getMyDiaryEntries();

    FarmDiaryResponse getDiaryEntryById(
            Long diaryId);

    FarmDiaryResponse updateDiaryEntry(
            Long diaryId,
            FarmDiaryRequest request);

    void deleteDiaryEntry(
            Long diaryId);

}