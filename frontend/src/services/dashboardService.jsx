import axiosInstance from "../utils/axiosInstance";

const API_URL = "http://localhost:8080/api/user/dashboard";

// Accept optional params object, e.g. { month: 2, year: 2026 }
export const getDashboardSummary = (params) => {
  return axiosInstance.get(`${API_URL}/summary`, { params });
};