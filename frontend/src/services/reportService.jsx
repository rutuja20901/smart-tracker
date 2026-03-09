import axios from "axios";

const API_BASE = "http://localhost:8080/api/user/report";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

// Monthly Report
export const getSummary = ({ month, year }) => {
  return axios.get(
    `${API_BASE}/monthly?month=${month}&year=${year}`,
    getAuthHeader()
  );
};

// Category Wise Report
export const getCategoryWise = ({ month, year }) => {
  return axios.get(
    `${API_BASE}/category-wise?month=${month}&year=${year}`,
    getAuthHeader()
  );
};