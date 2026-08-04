import api from "../api/axios";

export const loginUser = async (data) => {
  const response = await api.post(
    "/api/auth/login",
    data
  );

  // Return only the useful payload
  return response.data.data;
};

export const registerUser = async (data) => {
  const response = await api.post(
    "/api/auth/register",
    data
  );

  return response.data.data;
};
