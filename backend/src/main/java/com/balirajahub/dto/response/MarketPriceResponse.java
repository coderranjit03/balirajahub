package com.balirajahub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketPriceResponse {

    private String crop;

    private String market;

    private String district;

    private String state;

    private Double minimumPrice;

    private Double maximumPrice;

    private Double modalPrice;

    private String arrivalDate;
}