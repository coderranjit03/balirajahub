import axios from "axios";

export const geocodeVillage = async (
  village,
  district,
  state
) => {

  const query =
    `${village}, ${district}, ${state}, India`;

  const url =
    `https://nominatim.openstreetmap.org/search?q=${encodeURIComponent(query)}&format=json&limit=1`;

  const response = await axios.get(url);

  if (response.data.length > 0) {

    return {
      latitude: parseFloat(response.data[0].lat),
      longitude: parseFloat(response.data[0].lon),
    };
  }

  throw new Error("Location not found");
};
