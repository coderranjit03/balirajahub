package com.balirajahub.service;

import com.balirajahub.dto.response.MarketPriceResponse;

import java.util.List;

public interface MarketService {

    List<MarketPriceResponse> getMarketPrices(
            String crop,
            String district);
}