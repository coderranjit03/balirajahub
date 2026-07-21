package com.balirajahub.entity.enums;

public enum WeatherCode {

    CLEAR_SKY(0, "Clear Sky"),

    MAINLY_CLEAR(1, "Mainly Clear"),

    PARTLY_CLOUDY(2, "Partly Cloudy"),

    OVERCAST(3, "Overcast"),

    FOG(45, "Fog"),

    DEPOSITING_RIME_FOG(48, "Depositing Rime Fog"),

    DRIZZLE(51, "Light Drizzle"),

    MODERATE_DRIZZLE(53, "Moderate Drizzle"),

    DENSE_DRIZZLE(55, "Dense Drizzle"),

    RAIN(61, "Light Rain"),

    MODERATE_RAIN(63, "Moderate Rain"),

    HEAVY_RAIN(65, "Heavy Rain"),

    SNOW(71, "Snow"),

    THUNDERSTORM(95, "Thunderstorm"),

    UNKNOWN(-1, "Unknown");

    private final int code;

    private final String description;

    WeatherCode(int code, String description) {

        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescription(int code) {

        for (WeatherCode weatherCode : values()) {

            if (weatherCode.code == code) {
                return weatherCode.description;
            }
        }

        return UNKNOWN.description;
    }
}
