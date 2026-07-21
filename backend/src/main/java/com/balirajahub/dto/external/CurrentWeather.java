package com.balirajahub.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentWeather {

    @JsonProperty("temperature_2m")
    private Double temperature;

    @JsonProperty("wind_speed_10m")
    private Double windSpeed;

    @JsonProperty("weather_code")
    private Integer weatherCode;
}