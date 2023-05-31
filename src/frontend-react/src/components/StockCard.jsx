import React, { useState, useEffect } from "react";
import { Card } from "react-bootstrap";
import { API } from "../api";
import { useAuth } from "../contexts/AuthContext";

const StockCard = () => {
  const { userToken } = useAuth();
  const [result, setResult] = useState([]);

  const retrieveStocks = async () => {
    try {
      const response = await API.get("/api/v1/stocks/");
      setResult(response.data);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    retrieveStocks();
  }, []);

  return (
    <div
      style={{
        maxHeight: "100vh",
        height: "100vh",
        display: "flex",
        flexWrap: "wrap",
        padding: "2rem",
      }}
    >
      {result.map((stock, index) => {
        return (
          <div style={{ padding: "1rem" }}>
            <Card
              className="text-center"
              style={{
                width: "16rem",
                height: "auto",
                borderRadius: "14px",
                backgroundColor: "rgb(238, 238, 238)",
              }}
              key={index}
            >
              <Card.Body>
                <Card.Title>{stock.name}</Card.Title>
                <Card.Subtitle className="mb-2 text-muted">
                  {stock.ticker}
                </Card.Subtitle>
                <Card.Text>
                  Price: ${stock.price.toFixed(2)}
                  <br />
                  Volume: {stock.volume}
                  <br />
                </Card.Text>
                {userToken && (
                  <button
                    style={{
                      border: "none",
                      backgroundColor: "#50C878",
                      width: "4rem",
                      height: "auto",
                      borderRadius: "6px",
                      color: "#eee",
                    }}
                  >
                    Buy
                  </button>
                )}
              </Card.Body>
            </Card>
          </div>
        );
      })}
    </div>
  );
};

export default StockCard;
