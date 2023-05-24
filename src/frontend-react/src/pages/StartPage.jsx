import React, { useState } from "react";
import Login from "../pages/Login";
import stockImage from "../assets/stocks.png";
import { useAuth } from "../contexts/AuthContext";

const StartPage = () => {
  const [showSignup, setShowSignup] = useState(false);
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
          Empower yourself with the ultimate stock trading experience. Our
          cutting-edge app is designed to bring you seamless access to the
          dynamic world of stocks and investments. Whether you're a seasoned
          trader or just starting out, Stock Tradify is your gateway to
          financial growth and success.
        </article>

        <span style={{ fontWeight: "500" }}>With Stock Tradify, you can:</span>
        <ul style={{ width: "500px", marginTop: "1rem" }}>
          <li>
            Trade with Confidence: Execute trades swiftly and securely with our
            intuitive and user-friendly interface.
          </li>
          <li>
            Take advantage of real-time market data and stay ahead of the game.
          </li>
          <li>
            Discover Opportunities: Explore a vast array of stocks, from
            established companies to emerging trends.
          </li>
          <li>
            Personalize Your Portfolio: Tailor your investment strategy to match
            your goals and risk tolerance.
          </li>
          <li>Build a diversified portfolio and watch it grow over time.</li>
          {/* <li>
            Stay Informed: Get instant updates on market trends, stock prices,
            and breaking news that impact your investments.
          </li> */}
          <li>
            Our intelligent alerts keep you informed, even when you're on the
            move.
          </li>
          {/* <li>
            Learn and Grow: Expand your knowledge with educational resources,
            expert insights, and interactive tutorials.
          </li> */}
          <li>
            Master the art of trading and unlock your potential as a savvy
            investor.
          </li>
          {/* <li>
            Connect with a Community: Engage with like-minded traders, share
            ideas, and discuss strategies.
          </li> */}
          {/* <li>
            Tap into the collective wisdom of a vibrant community of investors.
          </li> */}
          <li>
            Security and Support: Rest assured that your data and transactions
            are safeguarded with robust security measures.
          </li>
          {/* <li>
            Our dedicated support team is always ready to assist you on your
            trading journey.
          </li> */}
        </ul>
        <span style={{ margin: "2rem 0rem", width: "500px" }}>
          Join us at Stock Tradify and embark on a thrilling adventure in the
          world of stocks. Unleash your potential and let your investments
          flourish. Start trading smarter today!
        </span>

        <p style={{ textDecoration: "underline", width: "500px" }}>
          Disclaimer: Stock trading involves risks. It is important to do
          thorough research and consult with a financial advisor before making
          any investment decisions.
        </p>
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
