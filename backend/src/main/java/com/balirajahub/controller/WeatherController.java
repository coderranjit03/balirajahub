package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.response.WeatherResponse;
import com.balirajahub.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/farmer/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current")
    public ApiResponse<WeatherResponse> currentWeather() {

        return ApiResponse.success(
                "Weather fetched successfully.",
                weatherService.getCurrentWeather()
        );
    }
}