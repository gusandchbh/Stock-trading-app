import React from "react";
import "../App.css";

const StartPage = () => {
  return (
    <div
      style={{
        height: "100%",
        width: "100%",
        display: "flex",
        justifyContent: "center",
        marginTop: "5rem",
        padding: "2rem",
      }}
    >
      <div
        style={{
          flexDirection: "column",
          display: "flex",
          alignItems: "center",
        }}
      >
        <h1> Welcome to </h1>{" "}
        <h1 style={{ fontFamily: "Anton" }}> Stock Tradify</h1>
        <article
          style={{
            padding: "2rem",
            marginTop: "2rem",
            width: "70vw",
            border: "10px solid white",
            fontSize: "1.3rem",
          }}
        >
          Investors have been closely watching the stock market in recent
          months, as a number of high-profile companies have seen their share
          prices surge. Here are some of the most popular stocks that investors
          are currently watching: <br />
          <ul>
            <li>
              <strong> Tesla (TSLA):</strong> Shares of the electric car maker
              have been on a tear in recent months, driven by strong demand for
              its vehicles and optimism about the company's future growth
              prospects. Tesla's market capitalization recently surpassed $800
              billion, making it one of the most valuable companies in the
              world.
            </li>
            <li>
              <strong>Apple (AAPL):</strong>
              The tech giant has long been a favorite of investors, thanks to
              its dominant position in the smartphone market and its growing
              ecosystem of services and devices. Apple's shares have been on the
              rise in recent weeks, fueled in part by strong sales of its latest
              iPhone models.
            </li>
            <li>
              <strong>Amazon (AMZN):</strong> The e-commerce giant has seen its
              stock price soar in recent years, as more consumers turn to online
              shopping and Amazon expands into new markets. Despite facing
              regulatory scrutiny in some jurisdictions, Amazon's stock remains
              a popular pick among investors.
            </li>
            <li>
              <strong>Microsoft (MSFT):</strong> The software giant has
              benefited from the shift to remote work and online collaboration
              during the COVID-19 pandemic, as more businesses and individuals
              rely on its productivity tools and cloud services. Microsoft's
              stock has been on a steady upward trajectory in recent months.
            </li>
            <li>
              <strong>Alphabet (GOOGL):</strong> The parent company of Google
              has seen its stock price climb steadily over the past year, as the
              search giant continues to dominate the online advertising market
              and invests heavily in new technologies such as artificial
              intelligence and self-driving cars. While these stocks have
              performed well in recent months, it's important to remember that
              investing always carries risks, and past performance is no
              guarantee of future results. Investors should always do their own
              research and consult with a financial advisor before making any
              investment decisions.
            </li>
          </ul>
        </article>
      </div>
    </div>
  );
};

export default StartPage;
