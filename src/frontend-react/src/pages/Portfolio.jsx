import React, { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import { useAuth } from "../contexts/AuthContext";
import { API } from "../api";  // adjust the path according to your project structure
import PortfolioStockCard from "../components/PortfolioStockCard";
import TradeList from "../components/TradeList";

export const Portfolio = () => {
    const { userToken } = useAuth();
    const [portfolio, setPortfolio] = useState(null);

    useEffect(() => {
        API.get(`/api/v1/portfolio/${userToken.userId}`)
            .then((res) => {
                setPortfolio(res.data);
            })
            .catch((err) => {
                console.error(err);
            });
    }, [userToken]);

    return (
        <div style={{ height: "100vh" }}>
            <Card
                className="text-center"
                style={{
                    backgroundColor: "transparent",
                    border: "none",
                    height: "75vh",
                }}
            >
                <Card.Body className="profile-card">
                    <h2>Portfolio </h2>
                    {portfolio && (
                        <div>
                            <p>Account Balance: {portfolio.accountBalance}</p>
                            <p>Total Value: {portfolio.totalValue}</p>

                            <div style={{ display: "flex", justifyContent: "space-between", flexWrap: "wrap" }}>
                                <div>
                                    <h3>Stocks</h3>
                                    <PortfolioStockCard stocks={portfolio.stocks} />
                                </div>

                                <div>
                                    <h3>Trades</h3>
                                    <TradeList trades={portfolio.tradeList} />
                                </div>
                            </div>
                        </div>
                    )}
                </Card.Body>
            </Card>
            <div className="w-100 text-center mt-2"></div>
        </div>
    );
};

export default Portfolio;
