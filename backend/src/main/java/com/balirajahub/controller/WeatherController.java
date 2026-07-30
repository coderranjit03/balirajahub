package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/current")
    public ApiResponse<Map<String, Object>> getCurrentWeather(
            @RequestParam String district) {

        String url =
                "https://api.openweathermap.org/data/2.5/weather?q="
                        + district
                        + ",IN&units=metric&appid="
                        + apiKey;

        Map response =
                restTemplate.getForObject(url, Map.class);

        Map main = (Map) response.get("main");

        Object weatherObject =
                ((java.util.List<?>) response.get("weather"))
                        .get(0);

        Map weatherMap = (Map) weatherObject;

        Map<String, Object> result = new HashMap<>();

        result.put("temperature", main.get("temp"));
        result.put("humidity", main.get("humidity"));
        result.put("condition", weatherMap.get("description"));

        return ApiResponse.success(
                "Weather fetched successfully.",
                result
        );
    }
}
