package com.balirajahub.service.impl;

import com.balirajahub.dto.request.SchemeSearchRequest;
import com.balirajahub.dto.response.SchemeResponse;
import com.balirajahub.entity.Scheme;
import com.balirajahub.entity.enums.SchemeCategory;
import com.balirajahub.exception.SchemeNotFoundException;
import com.balirajahub.repository.SchemeRepository;
import com.balirajahub.service.SchemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchemeServiceImpl implements SchemeService {

    private final SchemeRepository schemeRepository;

    // ====================================
    // Get Scheme By Id
    // ====================================

    @Override
    public SchemeResponse getSchemeById(Long id) {

        Scheme scheme = schemeRepository.findById(id)

                .orElseThrow(() ->
                        new SchemeNotFoundException(
                                "Scheme not found."));

        return mapToResponse(scheme);
    }

    // ====================================
    // Search Schemes
    // ====================================

    @Override
    public Page<SchemeResponse> searchSchemes(
            SchemeSearchRequest request) {

        Sort sort = request.getDirection().equalsIgnoreCase("desc")

                ? Sort.by(request.getSortBy()).descending()

                : Sort.by(request.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(

                request.getPage(),

                request.getSize(),

                sort
        );

        return schemeRepository.searchSchemes(

                request.getCategory(),

                request.getKeyword(),

                pageable

        ).map(this::mapToResponse);
    }


    // ====================================
    // Mapper
    // ====================================

    private SchemeResponse mapToResponse(
            Scheme scheme) {

        return SchemeResponse.builder()

                .id(scheme.getId())

                .title(scheme.getTitle())

                .description(scheme.getDescription())

                .category(scheme.getCategory())

                .eligibility(scheme.getEligibility())

                .benefits(scheme.getBenefits())

                .officialLink(scheme.getOfficialLink())

                .build();
    }
}