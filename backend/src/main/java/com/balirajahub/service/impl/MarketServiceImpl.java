package com.balirajahub.service.impl;

import com.balirajahub.client.MarketClient;
import com.balirajahub.dto.external.MarketApiResponse;
import com.balirajahub.dto.response.MarketPriceResponse;
import com.balirajahub.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {

    private final MarketClient marketClient;

    @Override
    public List<MarketPriceResponse> getMarketPrices(
            String crop,
            String district) {

        MarketApiResponse response =
                marketClient.getMarketPrices(
                        crop,
                        district);

        return response.getRecords()

                .stream()

                .map(record ->

                        MarketPriceResponse.builder()

                                .crop(record.getCommodity())

                                .market(record.getMarket())

                                .district(record.getDistrict())

                                .state(record.getState())

                                .minimumPrice(record.getMinimumPrice())

                                .maximumPrice(record.getMaximumPrice())

                                .modalPrice(record.getModalPrice())

                                .arrivalDate(record.getArrivalDate())

                                .build()
                )

                .toList();
    }
}