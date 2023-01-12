import React from "react";
import { Link } from "react-router-dom";

export const Header = () => {
  return (
    <div
      style={{
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-evenly",
      }}
    >
      <Link to="/login">
        <h1 style={{ color: "#333" }}>Login</h1>
      </Link>
      <Link to="/register">
        <h1 style={{ color: "#333" }}>Register</h1>
      </Link>
      <Link to="/profile">
        <h1 style={{ color: "#333" }}>Profile</h1>
      </Link>
    </div>
  );
};
