import React, { createContext, useContext, useState, useEffect } from "react";
import { API } from "../api";
import parseJWT from "../utils/parseJWT";

const AuthContext = createContext();

export function useAuth() {
  return useContext(AuthContext);
}

export function AuthProvider({ children }) {
  const [userToken, setUserToken] = useState();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setUserToken(parseJWT(token));
      API.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    }
    setLoading(false);
  }, []);

  const login = async (username, password) => {
    const response = await API.post("api/v1/auth/authenticate", {
      username: username,
      password: password,
    });
    if (response.status === 200) {
      const token = response.data;
      localStorage.setItem("token", token);
      setUserToken(parseJWT(token));
      API.defaults.headers.common["Authorization"] = `Bearer ${response.data}`;
    } else {
      console.log(response.data);
    }
  };

  const signup = async (username, password, email) => {
    await API.post("api/v1/auth/register", {
      username: username,
      password: password,
      email: email,
    });
  };

  const logout = () => {
    setUserToken(undefined);
    localStorage.clear();
    delete API.defaults.headers.common["Authorization"];
  };

  return (
    <AuthContext.Provider value={{ loading, userToken, login, signup, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export default AuthProvider;
