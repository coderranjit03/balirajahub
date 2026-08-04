import api from "../api/axios";

export const getDashboardStats = async () => {
  const response = await api.get("/api/dashboard/summary");

  console.log("Dashboard Summary:", response.data);

  return response.data.data;
};