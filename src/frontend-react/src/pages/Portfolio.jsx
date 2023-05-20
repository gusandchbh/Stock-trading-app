import React from "react";
import { Card } from "react-bootstrap";
import { useAuth } from "../contexts/AuthContext";

export const Portfolio = () => {
  const { userToken } = useAuth();
  console.log(userToken);

  return (
    <div>
      <Card
        className="text-center"
        style={{
          backgroundColor: "transparent",
          border: "none",
          height: "75vh",
        }}
      >
        <Card.Body className="profile-card">
          <h2>Portfolio </h2>
        </Card.Body>
      </Card>
      <div className="w-100 text-center mt-2"></div>
    </div>
  );
};

export default Portfolio;
