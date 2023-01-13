import React, { useState, useEffect } from "react";
import { Card } from "react-bootstrap";
import { useAuth } from "../contexts/AuthContext";

export const Profile = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { currentUser } = useAuth();

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
          {loading ? (
            <p>Loading...</p>
          ) : error ? (
            <p>Error: {error.message}</p>
          ) : (
            <>
              <h3>Welcome {currentUser}</h3>
            </>
          )}
        </Card.Body>
      </Card>
      <div className="w-100 text-center mt-2"></div>
    </div>
  );
};

export default Profile;
