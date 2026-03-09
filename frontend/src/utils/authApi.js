//Auth API (connects frontend → backend)

import axiosInstace from "../utils/axiosInstance";

const API_URL = "http://localhost:8080/auth";

export const loginUser = (data) =>
    axiosInstace.post(`${API_URL}/login`,data);

export const registerUser = (data) =>
    axiosInstace.post(`${API_URL}/register`,data);
