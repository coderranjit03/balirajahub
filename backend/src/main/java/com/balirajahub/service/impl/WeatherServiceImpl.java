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
import com.balirajahub.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    // ===========================
    // Dependencies
    // ===========================

    private final WeatherClient weatherClient;

    private final FarmerProfileRepository farmerProfileRepository;

    private final UserRepository userRepository;

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

        System.out.println(response);

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

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));
    }

    private FarmerProfile getCurrentFarmerProfile() {

        User user = getCurrentUser();

        return farmerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new FarmerProfileNotFoundException(
                                "Farmer profile not found."));
    }
}