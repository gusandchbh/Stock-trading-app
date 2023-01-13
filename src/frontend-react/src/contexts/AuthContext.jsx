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
    if (response.status === 200) {
        setCurrentUser(username);
        API.defaults.headers.common["Authorization"] = `Bearer ${response.data}`;
    }
  };

  const signup = async (username, password, email) => {
    const response = await API.post("users/register", {
      username: username,
      password: password,
      email: email,
    });
    if (response.status === 201){
      await signin(username, password)
    }
  };

  const logout = () => {
    setCurrentUser(undefined)
  }

  return (
    <AuthContext.Provider value={{ currentUser, signin, signup, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export default AuthProvider;
