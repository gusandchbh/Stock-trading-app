import React from "react";
import { ListGroup, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const PortfolioStockList = ({ stocks }) => {
  const navigate = useNavigate();

  if (!stocks) {
    return (
      <Spinner animation="border" role="status">
        <span className="sr-only">Loading stocks...</span>
      </Spinner>
    );
  }

  if (stocks.length === 0) {
    return <p>No stocks to display.</p>;
  }

  return (
    <ListGroup variant="flush">
      {stocks.map((stock, index) => (
        <ListGroup.Item
          key={index}
          className="bg-dark text-light"
          style={{ cursor: "pointer" }}
          onClick={() => navigate(`/sell/${stock.marketStock.id}`)}
        >
          <strong>{stock.stockName}</strong>
          <br />
          Total Value: ${stock.totalValue.toFixed(2)}
          <br />
          Quantity: {stock.quantity}
        </ListGroup.Item>
      ))}
    </ListGroup>
  );
};

export default PortfolioStockList;
