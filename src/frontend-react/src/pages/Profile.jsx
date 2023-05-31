import React from "react";
import { Card } from "react-bootstrap";
import { useAuth } from "../contexts/AuthContext";

export const Profile = () => {
  const { userToken } = useAuth();

  return (
    <div style={{ height: "100vh" }}>
      <Card
        className="text-center"
        style={{
          backgroundColor: "transparent",
          border: "none",
          height: "75vh",
        }}
      >
        <Card.Body className="profile-card">
          <h1>Welcome {userToken ? userToken.sub : "no user"}!</h1>
        </Card.Body>
      </Card>
      <div className="w-100 text-center mt-2"></div>
    </div>
  );
};

export default Profile;
