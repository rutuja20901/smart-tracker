import axios from "axios";
import { getToken } from "./jwt";

const axiosInstance = axios.create({
        baseURL: "https://expense-tracker-api.onrender.com/auth",
        headers:{
                "Content-Type" : "application/json",
        },
});

// Attach Authorization header if token exists
axiosInstance.interceptors.request.use(
    (config) => {
        const token = getToken();
        if (token) {
            config.headers = config.headers || {};
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default axiosInstance;