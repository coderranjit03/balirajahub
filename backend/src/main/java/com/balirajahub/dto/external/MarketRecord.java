package com.balirajahub.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketRecord {

    private String state;

    private String district;

    private String market;

    private String commodity;

    private String variety;

    private String grade;

    @JsonProperty("arrival_date")
    private String arrivalDate;

    @JsonProperty("min_price")
    private Double minimumPrice;

    @JsonProperty("max_price")
    private Double maximumPrice;

    @JsonProperty("modal_price")
    private Double modalPrice;
}