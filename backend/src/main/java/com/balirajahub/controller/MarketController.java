package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.response.MarketPriceResponse;
import com.balirajahub.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/market")
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;

    @GetMapping("/prices")
    public ApiResponse<List<MarketPriceResponse>> getPrices(

            @RequestParam(required = false)
            String crop,

            @RequestParam(required = false)
            String district) {

        return ApiResponse.success(

                "Market prices fetched successfully.",

                marketService.getMarketPrices(
                        crop,
                        district)
        );
    }
}