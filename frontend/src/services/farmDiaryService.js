import api from "../api/axios";

const BASE_URL = "/api/farm-diary";

// Get all diary entries
export const getAllDiaryEntries = async () => {
  const response = await api.get(BASE_URL);

  return response.data.data;
};

// Create new diary entry
export const createDiaryEntry = async (entryData) => {
  const response = await api.post(BASE_URL, entryData);

  return response.data.data;
};

// Get diary entry by ID
export const getDiaryEntryById = async (id) => {
  const response = await api.get(`${BASE_URL}/${id}`);

  return response.data.data;
};

// Update diary entry
export const updateDiaryEntry = async (id, entryData) => {
  const response = await api.put(
    `${BASE_URL}/${id}`,
    entryData
  );

  return response.data.data;
};

// Delete diary entry
export const deleteDiaryEntry = async (id) => {
  await api.delete(`${BASE_URL}/${id}`);
};