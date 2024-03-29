import React from "react";
import { Card } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const PortfolioStockCard = ({ stocks }) => {
    const navigate = useNavigate();

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
            {stocks.map((stock, index) => {
                return (
                    <div style={{ padding: "1rem" }} key={index} onClick={() => navigate(`/sell/${stock.id}`)}>
                        <Card
                            className="text-center"
                            style={{
                                width: "16rem",
                                height: "auto",
                                borderRadius: "14px",
                                backgroundColor: "rgb(238, 238, 238)",
                                cursor: "pointer",
                            }}
                        >
                            <Card.Body>
                                <Card.Title>{stock.stockName}</Card.Title>
                                <Card.Text>
                                    Total Value: ${stock.totalValue.toFixed(2)}
                                    <br />
                                    Quantity: {stock.quantity}
                                    <br />
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </div>
                );
            })}
        </div>
    );
};

export default PortfolioStockCard;
