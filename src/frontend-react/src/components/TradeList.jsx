import React from "react";
import { Table, Spinner } from "react-bootstrap";
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

    return (
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
            {trades.map((trade) => (
                <TradeRow key={trade.id} trade={trade} />
            ))}
            </tbody>
        </Table>
    );
};

export default PortfolioTradeList;
