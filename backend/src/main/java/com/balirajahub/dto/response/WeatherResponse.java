package com.balirajahub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherResponse {

    private Double temperature;

    private Double windSpeed;

    private Integer weatherCode;

    private String weatherDescription;
}