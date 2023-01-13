import React, { createContext, useContext, useState } from "react";
import API from "../api";

const AuthContext = createContext();

export function useAuth() {
  return useContext(AuthContext);
}

export function AuthProvider({ children }) {
  const [currentUser, setCurrentUser] = useState();

  const signin = async (username, password) => {
    const response = await API.post("users/login", {
      username: username,
      password: password,
    });
    setCurrentUser(response.data);
    API.defaults.headers.common["Authorization"] = `Bearer ${response.data}`;
  };

  const signup = async (username, password, email) => {
    await API.post("users/register", {
      username: username,
      password: password,
      email: email,
    });
  };

  return (
    <AuthContext.Provider value={{ currentUser, signin, signup }}>
      {children}
    </AuthContext.Provider>
  );
}

export default AuthProvider;
