import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { Button } from "react-bootstrap";
import "../App.css";

export const Header = () => {
  const { currentUser, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div
      style={{
        display: "grid",
        gridTemplateColumns: "1fr auto",
        alignItems: "center",
        backgroundColor: "#91b391",
        height: "120px",
        padding: "0 1rem",
        width: "100%",
      }}
    >
      <div style={{ gridRow: "1", gridColumn: "1", textAlign: "center" }}>
        <Link to="/" style={{ textDecoration: "none", fontFamily: "Anton" }}>
          <h1 style={{ color: "#333", fontSize: "2.5rem" }}>Stock Tradify</h1>
        </Link>
      </div>
      <div
        style={{ display: "flex", justifyContent: "flex-end", gridColumn: "2" }}
      >
        {!currentUser ? (
          <>
            <Link
              to="/login"
              style={{ textDecoration: "none", marginRight: "1rem" }}
            >
              <h3 style={{ color: "#333" }}>Sign In</h3>
            </Link>
            {/* <Link to="/register" style={{ textDecoration: "none" }}>
              <h3 style={{ color: "#333" }}>Sign Up</h3>
            </Link> */}
          </>
        ) : (
          <>
            <Link
              to="/stocks"
              style={{ textDecoration: "none", marginRight: "1rem" }}
            >
              <h3 style={{ color: "#333" }}>Stocks</h3>
            </Link>
            <Link to="/profile" style={{ textDecoration: "none" }}>
              <h3 style={{ color: "#333" }}>Profile</h3>
            </Link>
            <Button
              style={{
                backgroundColor: "transparent",
                border: "none",
                color: "#333",
                fontSize: "1.7rem",
                fontWeight: "500",
                marginLeft: "1rem",
              }}
              onClick={handleLogout}
            >
              Sign out
            </Button>
          </>
        )}
      </div>
    </div>
  );
};
