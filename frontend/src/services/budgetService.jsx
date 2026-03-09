import axiosInstance from "../utils/axiosInstance";

const API = "/api/user/budget";

export const getBudget = (userId) => axiosInstance.get(`${API}/${userId}`);

export const createBudget = (payload) => axiosInstance.post(API, payload);

export const updateBudget = (id, payload) => axiosInstance.put(`${API}/${id}`, payload);

export const deleteBudget = (id) => axiosInstance.delete(`${API}/${id}`);

export default { getBudget, createBudget, updateBudget ,deleteBudget};
