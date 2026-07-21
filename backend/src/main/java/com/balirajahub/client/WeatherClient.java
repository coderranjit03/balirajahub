package com.balirajahub.client;

import com.balirajahub.dto.external.OpenMeteoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final RestTemplate restTemplate;

    public OpenMeteoResponse getCurrentWeather(
            Double latitude,
            Double longitude) {

        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.open-meteo.com/v1/forecast")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam(
                        "current",
                        "temperature_2m,wind_speed_10m,weather_code")
                .toUriString();

        return restTemplate.getForObject(
                url,
                OpenMeteoResponse.class
        );
    }
}