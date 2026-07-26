package com.balirajahub.service.impl;

import com.balirajahub.dto.ai.AIAdviceResponse;
import com.balirajahub.dto.ai.AIContext;
import com.balirajahub.dto.ai.GeminiResponse;
import com.balirajahub.dto.response.MarketPriceResponse;
import com.balirajahub.dto.response.WeatherResponse;
import com.balirajahub.entity.Crop;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.User;
import com.balirajahub.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIAdvisoryServiceImpl
        implements AIAdvisoryService {

    private final GeminiService geminiService;

    private final AuthenticatedUserService authenticatedUserService;

    private final FarmerProfileService farmerProfileService;

    private final CropService cropService;

    private final WeatherService weatherService;

    private final MarketService marketService;

    private final PromptBuilderService promptBuilderService;


    @Override
    public AIAdviceResponse generateAdvice() {

        User user = authenticatedUserService.getCurrentUser();

        FarmerProfile farmerProfile =
                farmerProfileService.getCurrentFarmerProfile();

        List<Crop> crops =
                cropService.getCurrentFarmerCrops();

        WeatherResponse weather =
                weatherService.getCurrentWeather();

        List<MarketPriceResponse> marketPrices =
                getMarketPrices(crops, farmerProfile);

        AIContext context = AIContext.builder()
                .user(user)
                .farmerProfile(farmerProfile)
                .crops(crops)
                .weather(weather)
                .marketPrices(marketPrices)
                .schemes(Collections.emptyList())
                .build();

        String prompt =
                promptBuilderService.buildPrompt(context);

        GeminiResponse response =
                geminiService.generateContent(prompt);

        return AIAdviceResponse.builder()
                .advice(response.getContent())
                .build();
    }


    private List<MarketPriceResponse> getMarketPrices(
            List<Crop> crops,
            FarmerProfile farmerProfile) {

        List<MarketPriceResponse> marketPrices =
                new ArrayList<>();

        for (Crop crop : crops) {

            marketPrices.addAll(

                    marketService.getMarketPrices(

                            crop.getCropName(),

                            farmerProfile.getDistrict()
                    )
            );
        }

        return marketPrices;
    }
}