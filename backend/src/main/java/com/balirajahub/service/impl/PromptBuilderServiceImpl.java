package com.balirajahub.service.impl;

import com.balirajahub.dto.ai.AIContext;
import com.balirajahub.entity.Crop;
import com.balirajahub.service.PromptBuilderService;
import org.springframework.stereotype.Service;

@Service
public class PromptBuilderServiceImpl
        implements PromptBuilderService {

    @Override
    public String buildPrompt(AIContext context) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are an expert agricultural advisor for Indian farmers.

                Your job is to provide practical, accurate, and easy-to-understand farming advice.

                Always answer in simple language.

                Give actionable recommendations.

                Keep the response under 250 words.

                ----------------------------------------

                FARMER DETAILS

                """);

        prompt.append("Name : ")
                .append(context.getUser().getFirstName())
                .append(" ")
                .append(context.getUser().getLastName())
                .append("\n");

        if (context.getFarmerProfile() != null) {

            prompt.append("Village : ")
                    .append(context.getFarmerProfile().getVillage())
                    .append("\n");

            prompt.append("District : ")
                    .append(context.getFarmerProfile().getDistrict())
                    .append("\n");

            prompt.append("State : ")
                    .append(context.getFarmerProfile().getState())
                    .append("\n");
        }

        prompt.append("\n----------------------------------------\n");

        prompt.append("CROPS\n\n");

        if (context.getCrops() == null ||
                context.getCrops().isEmpty()) {

            prompt.append("No crops available.\n");

        } else {

            for (Crop crop : context.getCrops()) {

                prompt.append("- Crop : ")
                        .append(crop.getCropName())
                        .append("\n");

                prompt.append("  Season : ")
                        .append(crop.getSeason())
                        .append("\n");

                prompt.append("  Area : ")
                        .append(crop.getArea())
                        .append(" Acre\n");

                prompt.append("  Sowing Date : ")
                        .append(crop.getSowingDate())
                        .append("\n");

                prompt.append("  Expected Harvest : ")
                        .append(crop.getExpectedHarvestDate())
                        .append("\n");

                prompt.append("  Status : ")
                        .append(crop.getStatus())
                        .append("\n\n");
            }
        }

        prompt.append("----------------------------------------\n");

        prompt.append("WEATHER\n\n");

        prompt.append(context.getWeather());

        prompt.append("\n\n----------------------------------------\n");

        prompt.append("MARKET PRICE\n\n");

        prompt.append(context.getMarketPrices());

        prompt.append("\n\n----------------------------------------\n");

        prompt.append("GOVERNMENT SCHEMES\n\n");

        prompt.append(context.getSchemes());

        prompt.append("""

                ----------------------------------------

                Based on the above information provide:

                1. Farming Advice

                2. Weather Precautions

                3. Irrigation Suggestions

                4. Market Selling Advice

                5. Fertilizer/Pesticide Suggestions if required

                6. Mention any useful government scheme

                Keep the response concise and practical.
                """);

        return prompt.toString();
    }
}