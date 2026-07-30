import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.request.use((config) => {

  const token = localStorage.getItem("token");

  // Skip auth endpoints
  const isAuthRequest =
    config.url?.includes("/api/auth/login") ||
    config.url?.includes("/api/auth/register");

  if (token && !isAuthRequest) {

    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;
