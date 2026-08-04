import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  console.log("JWT TOKEN:", token);

  const isAuthRequest =
    config.url?.includes("/api/auth/login") ||
    config.url?.includes("/api/auth/register");

  if (token && !isAuthRequest) {
    config.headers = config.headers || {};

    config.headers.Authorization = `Bearer ${token}`;

    console.log(
      "Authorization header set:",
      config.headers.Authorization
    );
  }

  return config;
});

export default api;
