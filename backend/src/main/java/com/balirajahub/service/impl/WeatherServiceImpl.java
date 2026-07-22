package com.balirajahub.service.impl;

import com.balirajahub.client.WeatherClient;
import com.balirajahub.dto.external.OpenMeteoResponse;
import com.balirajahub.dto.response.WeatherResponse;
import com.balirajahub.entity.FarmerProfile;
import com.balirajahub.entity.User;
import com.balirajahub.entity.enums.WeatherCode;
import com.balirajahub.exception.FarmerProfileNotFoundException;
import com.balirajahub.exception.UserNotFoundException;
import com.balirajahub.repository.FarmerProfileRepository;
import com.balirajahub.repository.UserRepository;
import com.balirajahub.service.AuthenticatedUserService;
import com.balirajahub.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    // ===========================
    // Dependencies
    // ===========================

    private final WeatherClient weatherClient;

    private final FarmerProfileRepository farmerProfileRepository;

    private final AuthenticatedUserService authenticatedUserService;

    private static final Logger logger =
            LoggerFactory.getLogger(WeatherServiceImpl.class);

    // ===========================
    // Service Methods
    // ===========================

    @Override
    public WeatherResponse getCurrentWeather() {

        FarmerProfile farmerProfile = getCurrentFarmerProfile();

        Double latitude = farmerProfile.getLatitude();

        Double longitude = farmerProfile.getLongitude();

         OpenMeteoResponse response =
                weatherClient.getCurrentWeather(
                        latitude,
                        longitude);

        logger.debug("Weather API Response: {}", response);

        return WeatherResponse.builder()
                .temperature(response.getCurrent().getTemperature())
                .windSpeed(response.getCurrent().getWindSpeed())
                .weatherCode(response.getCurrent().getWeatherCode())
                .weatherDescription(
                        WeatherCode.getDescription(
                                response.getCurrent().getWeatherCode()))
                .build();
    }

    // ===========================
    // Helper Methods
    // ===========================



    private FarmerProfile getCurrentFarmerProfile() {

        User user = authenticatedUserService.getCurrentUser();

        return farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));
    }
}