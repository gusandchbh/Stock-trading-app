import React from "react";
import { Card } from "react-bootstrap";
import StockCard from "../components/StockCard";
import SimpleBar from "simplebar-react";
import "simplebar-react/dist/simplebar.min.css";

const StockPage = () => {
  return (
    <div className="vh-100 vw-100 bg-dark d-flex text-light">
      <Card
        className="text-center bg-dark border-0 m-0 d-flex flex-column align-items-center"
        style={{ width: "100%", height: "100%" }}
      >
        <h1 className="text-light display-4 mt-4"> Stocks </h1>
        <div className="w-100 overflow-auto" style={{ flex: 1 }}>
          <SimpleBar style={{ maxHeight: "100%" }}>
            <StockCard />
          </SimpleBar>
        </div>
      </Card>
    </div>
  );
};

export default StockPage;
