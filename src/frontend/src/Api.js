import axios from "axios";

export const Api = axios.create({
  baseURL: "http://localhost:8080/",
});

/*
Api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);
*/
