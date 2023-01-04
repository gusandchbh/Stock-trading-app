import axios from "axios";
import { useAuthStore } from "@/stores/auth";
export const Api = axios.create({
  baseURL: "http://localhost:8080/",
});

Api.interceptors.request.use(
  (config) => {
    const token = useAuthStore().token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);
