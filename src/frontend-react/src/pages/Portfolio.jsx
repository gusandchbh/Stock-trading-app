import React, { useEffect, useState } from "react";
import { Card, Col, Row, Spinner } from "react-bootstrap";
import { useAuth } from "../contexts/AuthContext";
import { API } from "../api";  // adjust the path according to your project structure
import PortfolioStockList from "../components/PortfolioStockList";
import TradeList from "../components/TradeList";
import SimpleBar from 'simplebar-react';
import 'simplebar-react/dist/simplebar.min.css';

export const Portfolio = () => {
    const {userToken} = useAuth();
    const [portfolio, setPortfolio] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (userToken) {
            API.get(`/api/v1/portfolio/${userToken.userId}`)
                .then((res) => {
                    setPortfolio(res.data);
                    setLoading(false);
                })
                .catch((err) => {
                    console.error(err);
                });
        }
    }, [userToken]);


    return (
        <div className="vh-100 bg-dark d-flex text-light">
            <Card className="text-center bg-dark border-0 mx-auto" style={{width: "90vw", height: "80vh"}}>
                <Card.Body className="d-flex flex-column align-items-center text-light">
                    {portfolio ? (
                        <div>
                            <p className="text-light display-4">Account Balance: ${portfolio.accountBalance.toFixed(2)}</p>
                            <p className="text-light display-4">Total Value: ${portfolio.totalValue.toFixed(2)}</p>

                            <div className="d-flex mt-4" style={{width: '100%'}}>
                                <div style={{width: '253.98px', maxHeight: '349.49px'}}>
                                    <h3 className="mb-3 text-light">Stocks</h3>
                                    <SimpleBar style={{maxHeight: '300px'}}>
                                        <PortfolioStockList stocks={portfolio.stocks}/>
                                    </SimpleBar>
                                </div>
                                <div style={{width: '507.96px', maxHeight: '349.49px'}}>
                                    <h3 className="mb-3 text-light">Trades</h3>
                                    <SimpleBar style={{maxHeight: '300px'}}>
                                        <TradeList trades={portfolio.tradeList}/>
                                    </SimpleBar>
                                </div>
                            </div>
                        </div>
                    ) : (
                        <div className="d-flex justify-content-center">
                            <Spinner animation="border" role="status">
                                <span className="sr-only">Loading...</span>
                            </Spinner>
                        </div>
                    )}
                </Card.Body>
            </Card>
        </div>
    );
};

export default Portfolio;
