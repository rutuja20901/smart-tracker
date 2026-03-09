import axiosInstance from "../utils/axiosInstance";

const API_URL = "/api/user/expense";

// GET /api/user/expenses?month=2&year=2026
export const getExpenses = (params) => axiosInstance.get(API_URL, { params });

// POST /api/user/expenses
export const addExpense = (payload) => axiosInstance.post(API_URL, payload);

// DELETE /api/user/expenses/{id}
export const deleteExpense = (id) => axiosInstance.delete(`${API_URL}/${id}`);

export default {
  getExpenses,
  addExpense,
  deleteExpense,
};
