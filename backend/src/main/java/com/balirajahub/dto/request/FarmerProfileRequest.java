package com.balirajahub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmerProfileRequest {

    @NotBlank
    private String village;

    @NotBlank
    private String taluka;

    @NotBlank
    private String district;

    @NotBlank
    private String state;

    @NotBlank
    private String pinCode;

    @NotNull
    private Double farmSize;
}