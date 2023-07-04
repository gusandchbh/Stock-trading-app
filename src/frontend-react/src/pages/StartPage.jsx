import React, { useState } from "react";
import Login from "../pages/Login";
import stockImage from "../assets/stocks.png";
import { useAuth } from "../contexts/AuthContext";

const StartPage = () => {
  const [showSignup] = useState(false);

  const { userToken } = useAuth();

  return (
    <div
      style={{
        height: "200rem",
        width: "100%",
        display: "flex",
        justifyContent: "center",
        padding: "2rem",
      }}
    >
      <div style={{ padding: "2rem", margin: " 0rem 0rem 2rem 2rem" }}>
        <h2>Welcome to Stock Tradify! </h2>
        <article style={{ width: "500px", marginBottom: "1rem" }}>
          This application is a personal side project developed in my spare
          time, aiming to simulate a stock trading platform. It allows users to
          invest fictional money and explore many standard features found in
          real trading applications.
          <br />
          <br />
          The project, still under development, incorporates technologies such
          as Java 17, Spring Boot, MariaDB, and third-party APIs like Alpha
          Vantage. It includes user authentication using JWT, password policy
          enforcement with Passay, and user-to-user communication. The ultimate
          goal is to serve as a learning platform and an opportunity to
          experiment with a wide range of technologies in a hands-on manner, all
          while working towards creating a feature-rich stock trading
          simulation.
        </article>
      </div>
      {!userToken ? (
        <div style={{ padding: "4rem 4rem" }}>
          {!showSignup && <Login showSignup={showSignup} />}
        </div>
      ) : (
        <div style={{ padding: "4rem 4rem" }}>
          <img src={stockImage} style={{ width: "400px", height: "auto" }} />
        </div>
      )}
    </div>
  );
};

export default StartPage;
