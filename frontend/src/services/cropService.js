import api from "../api/axios";

// Base path matches backend controller
const BASE_URL = "/api/farmer/crops";

export const getAllCrops = async () => {
  const response = await api.get(BASE_URL);

  return response.data.data;
};

export const createCrop = async (cropData) => {
  const response = await api.post(BASE_URL, cropData);

  return response.data.data;
};

export const getCropById = async (id) => {
  const response = await api.get(`${BASE_URL}/${id}`);

  return response.data.data;
};

export const updateCrop = async (id, cropData) => {
  const response = await api.put(
    `${BASE_URL}/${id}`,
    cropData
  );

  return response.data.data;
};

export const deleteCrop = async (id) => {
  await api.delete(`${BASE_URL}/${id}`);
};
