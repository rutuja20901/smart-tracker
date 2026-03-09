import axios from "axios";

const API_URL = "http://localhost:8080/auth"; 
// change port/path if your backend differs

export const registerUser = (registerData) => {
  return axios.post(`${API_URL}/register`, registerData);
};
