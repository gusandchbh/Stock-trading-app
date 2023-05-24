import React from "react";
import StockCard from "../components/StockCard";

const StockPage = () => {
  return (
    <div style={{ height: "100vh" }}>
      <div style={{ alignItems: "center" }}>
        <h1> Stocks </h1>
      </div>

      <div>
        <StockCard />
      </div>
    </div>
  );
};

export default StockPage;
