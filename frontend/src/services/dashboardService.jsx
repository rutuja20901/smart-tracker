import axiosInstance from "../utils/axiosInstance";

const API_URL = "/api/user/dashboard";

// Accept optional params object, e.g. { month: 2, year: 2026 }
export const getDashboardSummary = (params) => {
  return axiosInstance.get(`${API_URL}/summary`, { params });
};