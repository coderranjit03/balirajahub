package com.balirajahub.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FarmerProfileResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String village;

    private String taluka;

    private String district;

    private String state;

    private Double latitude;

    private Double longitude;

    private String pinCode;

    private Double farmSize;

    private String profileImage;
}