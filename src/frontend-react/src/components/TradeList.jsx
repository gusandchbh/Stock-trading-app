import React from "react";
import { Table } from "react-bootstrap";

const PortfolioTradeList = ({ trades }) => {
  if (!trades) {
    return <p>Loading trades...</p>;  // or return null or some other loading spinner
  }

  return (
      <Table striped bordered hover>
        <thead>
        <tr>
          <th>Type</th>
          <th>Shares</th>
          <th>Price Per Share</th>
          <th>Date</th>
          <th>Stock</th>
          <th>Price</th>
        </tr>
        </thead>
        <tbody>
        {trades.map((trade, index) => (
            <tr key={index}>
              <td>{trade.tradeType}</td>
              <td>{trade.shares}</td>
              <td>${trade.pricePerShare.toFixed(2)}</td>
              <td>{new Date(trade.createDate).toLocaleString().substring(0, 10)}</td>
              <td>{trade.marketStock.name} </td>
              <td>${trade.marketStock.price.toFixed(2)}</td>
            </tr>
        ))}
        </tbody>
      </Table>
  );
};

export default PortfolioTradeList;
