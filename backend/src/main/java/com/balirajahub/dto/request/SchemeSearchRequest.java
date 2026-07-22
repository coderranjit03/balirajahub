package com.balirajahub.dto.request;

import com.balirajahub.entity.enums.SchemeCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchemeSearchRequest {

    private SchemeCategory category;

    private String keyword;

    private Integer page = 0;

    private Integer size = 10;

    private String sortBy = "createdAt";

    private String direction = "desc";
}