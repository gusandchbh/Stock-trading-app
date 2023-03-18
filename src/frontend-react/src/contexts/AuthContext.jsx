import React, { createContext, useContext, useState } from "react";
import { API } from "../api";
import parseJWT from "../utils/parseJWT";

const AuthContext = createContext();

export function useAuth() {
  return useContext(AuthContext);
}

export function AuthProvider({ children }) {
  const [userToken, setUserToken] = useState(
    parseJWT(localStorage.getItem("token"))
  );

  const login = async (email, password) => {
    const response = await API.post("users/login", {
      email: email,
      password: password,
    });
    console.log(response);
    if (response.status === 200) {
      setUserToken(email);
      localStorage.token = response.data;
      API.defaults.headers.common["Authorization"] = `Bearer ${response.data}`;
    }
  };

  const signup = async (username, password, email) => {
    const response = await API.post("users/register", {
      username: username,
      password: password,
      email: email,
    });
    if (response.status === 201) {
      await login(username, password);
    }
  };

  const logout = () => {
    setUserToken(undefined);
    localStorage.clear();
  };

  return (
    <AuthContext.Provider value={{ userToken, login, signup, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export default AuthProvider;
