package com.balirajahub.service;

import com.balirajahub.dto.request.SchemeSearchRequest;
import com.balirajahub.dto.response.SchemeResponse;
import com.balirajahub.entity.enums.SchemeCategory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SchemeService {

    SchemeResponse getSchemeById(Long id);

    Page<SchemeResponse> searchSchemes(
            SchemeSearchRequest request);


}