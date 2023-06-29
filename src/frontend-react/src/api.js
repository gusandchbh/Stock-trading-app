import React from 'react';
import axios from 'axios';

export const API = axios.create({
    baseURL: `http://localhost:8080/`
});

API.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token')
        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }
        return config
    },
    error => Promise.reject(error)
)

/*API.interceptors.response.use(
    function (response) {
        return response;
    },
    function (error) {
        // Any status codes that fall outside the range of 2xx cause this function to trigger
        // We can check for the 401 Unauthorized error here
        if (error.response && error.response.status === 403) {
            logout();
            window.navigate('/'); // Redirect to login page using global navigate function
        }
        return Promise.reject(error);
    }
);*/

const logout = () => {
    localStorage.clear();
    delete API.defaults.headers.common["Authorization"];
};
