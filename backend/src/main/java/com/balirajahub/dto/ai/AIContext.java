package com.balirajahub.dto.ai;

import com.balirajahub.dto.response.MarketPriceResponse;
import com.balirajahub.dto.response.SchemeResponse;
import com.balirajahub.dto.response.WeatherResponse;
import com.balirajahub.entity.Crop;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIContext {

    private User user;

    private FarmerProfile farmerProfile;

    private List<Crop> crops;

    private WeatherResponse weather;

    private List<MarketPriceResponse> marketPrices;

    private List<SchemeResponse> schemes;

}