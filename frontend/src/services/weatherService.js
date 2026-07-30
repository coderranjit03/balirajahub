import api from "../api/axios";

export const getCurrentWeather = async (city = "Thane") => {
  try {
    const response = await api.get(
      `/api/weather/current?district=${city}`
    );

    return response.data.data;
  } catch (error) {
    console.error("Weather fetch failed:", error);

    // Temporary fallback
    return {
      city,
      temperature: 29,
      condition: "Sunny with light clouds",
      humidity: 72,
    };
  }
};
