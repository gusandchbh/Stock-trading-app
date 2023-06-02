import React, {useEffect} from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { Button } from "react-bootstrap";

export const Header = () => {
  const { userToken, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
        height: "10vh",
        alignItems: "center",
        backgroundColor: "#eee",
        padding: "2rem",
      }}
    >
      <Link to="/" style={{ textDecoration: "none" }}>
        <a style={{ color: "#333" }}>Stock Tradify ™</a>
      </Link>
      {userToken ? (
        <>
          <Link to="/stocks" style={{ textDecoration: "none" }}>
            <a style={{ color: "#333" }}>Stocks</a>
          </Link>
          <Link to="/profile" style={{ textDecoration: "none" }}>
            <a style={{ color: "#333" }}>Profile</a>
          </Link>
          <Link to="/portfolio" style={{ textDecoration: "none" }}>
            <a style={{ color: "#333" }}>Portfolio</a>
          </Link>
          <Button
            style={{
              color: "#333",
              backgroundColor: "transparent",
              border: "none",
            }}
            onClick={handleLogout}
          >
            Sign out
          </Button>
        </>
      ) : (
        <>
          <Link to="/stocks" style={{ textDecoration: "none" }}>
            <a style={{ color: "#333" }}>Stocks</a>
          </Link>
          <Link to="/" style={{ textDecoration: "none" }}>
            <a style={{ color: "#333" }}>Sign In</a>
          </Link>
        </>
      )}
    </div>
  );
};
