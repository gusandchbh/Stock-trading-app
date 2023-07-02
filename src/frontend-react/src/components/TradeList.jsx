import React, { useState, useEffect } from "react";
import { Table, Spinner, Form } from "react-bootstrap";
import moment from "moment";

const TradeRow = ({ trade }) => (
    <tr className="text-light">
        <td>{trade.tradeType}</td>
        <td>{trade.shares}</td>
        <td>${trade.pricePerShare.toFixed(2)}</td>
        <td>{moment(trade.createDate).format('YYYY/MM/DD')}</td>
        <td>{trade.marketStock.name}</td>
    </tr>
);

const PortfolioTradeList = ({ trades, loading, error }) => {
    const [searchTerm, setSearchTerm] = useState('');
    const [filteredTrades, setFilteredTrades] = useState(trades);

    useEffect(() => {
        const lowercasedSearchTerm = searchTerm.toLowerCase();
        const newFilteredTrades = trades.filter((trade) =>
            trade.tradeType.toLowerCase().includes(lowercasedSearchTerm) ||
            trade.shares.toString().includes(lowercasedSearchTerm) ||
            trade.pricePerShare.toFixed(2).includes(lowercasedSearchTerm) ||
            moment(trade.createDate).format('YYYY/MM/DD').includes(lowercasedSearchTerm) ||
            trade.marketStock.name.toLowerCase().includes(lowercasedSearchTerm)
        );

        setFilteredTrades(newFilteredTrades);
    }, [searchTerm, trades]);

    if (loading) {
        return (
            <div className="d-flex justify-content-center text-light">
                <Spinner animation="border" role="status">
                    <span className="sr-only">Loading trades...</span>
                </Spinner>
            </div>
        );
    }

    if (error) {
        return <p className="text-light">Error loading trades: {error.message}</p>;
    }

    if (!trades.length) {
        return <p className="text-light">Trades will be shown here</p>;
    }

    if (!filteredTrades.length) {
        return (
            <div>
                <Form.Control
                    type="text"
                    placeholder="Search..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="my-3"
                />
                <p className="text-light">No trades found</p>
            </div>
        );
    }

    return (
        <div>
            <Form.Control
                type="text"
                placeholder="Search..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="my-3"
            />
            <Table striped bordered hover variant="dark">
                <thead>
                <tr>
                    <th>Type</th>
                    <th>Shares</th>
                    <th>Price</th>
                    <th>Date</th>
                    <th>Stock</th>
                </tr>
                </thead>
                <tbody>
                {filteredTrades.map((trade) => (
                    <TradeRow key={trade.id} trade={trade} />
                ))}
                </tbody>
            </Table>
        </div>
    );
};

export default PortfolioTradeList;
