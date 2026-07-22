package com.balirajahub.dto.response;

import com.balirajahub.entity.enums.SchemeCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SchemeResponse {

    private Long id;

    private String title;

    private String description;

    private SchemeCategory category;

    private String eligibility;

    private String benefits;

    private String officialLink;
}