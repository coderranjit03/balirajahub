package com.balirajahub.dto.external;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MarketApiResponse {

    private List<MarketRecord> records;
}