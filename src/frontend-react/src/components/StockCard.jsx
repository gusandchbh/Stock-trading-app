import React, { useState, useEffect } from "react";
import { Card, Button, Modal } from "react-bootstrap";
import { API } from "../api";
import { useAuth } from "../contexts/AuthContext";
import PurchaseModal from "../pages/PurchaseModal";

const StockCard = () => {
  const { userToken } = useAuth();
  const [result, setResult] = useState([]);
  const [show, setShow] = useState(false);
  const [currentStockId, setCurrentStockId] = useState(null);

  const retrieveStocks = async () => {
    try {
      const response = await API.get("/api/v1/stocks/");
      setResult(response.data);
    } catch (e) {
      console.log(e);
    }
  };

  const handleOpen = (stockId) => {
    setCurrentStockId(stockId);
    setShow(true);
  };

  const handleClose = () => {
    setShow(false);
    setCurrentStockId(null);
  };

  useEffect(() => {
    retrieveStocks();
  }, []);

  return (
    <div className="d-flex flex-wrap justify-content-around p-3">
      {result.map((stock, index) => {
        return (
          <Card key={index} style={{ width: "18rem" }} className="mb-4 shadow">
            <Card.Body className="d-flex flex-column">
              <Card.Title>{stock.name}</Card.Title>
              <Card.Subtitle className="mb-2 text-muted">
                {stock.ticker}
              </Card.Subtitle>
              <Card.Text>Price: ${stock.price.toFixed(2)}</Card.Text>
              {userToken && (
                <Button
                  variant="dark"
                  className="mt-auto"
                  style={{ borderRadius: "6px" }}
                  onClick={() => handleOpen(stock.id)}
                >
                  Buy
                </Button>
              )}
            </Card.Body>
          </Card>
        );
      })}
      <PurchaseModal
        stockId={currentStockId}
        show={show}
        handleClose={handleClose}
      />
    </div>
  );
};

export default StockCard;
