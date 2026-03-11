import axios from "axios";

const API_URL = "https://expense-tracker-api.onrender.com/auth"; 
// change port/path if your backend differs

export const registerUser = (registerData) => {
  return axios.post(`${API_URL}/register`, registerData);
};
