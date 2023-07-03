import React, { useState, useMemo } from "react";
import { Table, Spinner, Form, Pagination } from "react-bootstrap";
import moment from "moment";
import { useQuery } from 'react-query';
import { API } from "../api";
import _ from 'lodash';

const TradeRow = ({ trade }) => (
    <tr>
        <td>{trade.tradeType}</td>
        <td>{trade.shares}</td>
        <td>{trade.pricePerShare}</td>
        <td>{moment(trade.createDate).format("DD/MM/YYYY")}</td>
        <td>{trade.marketStock.name}</td>
    </tr>
);

const PortfolioTradeList = ({ portfolioId }) => {
    const [searchTerm, setSearchTerm] = useState('');
    const [currentPage, setCurrentPage] = useState(1);

    const fetchTrades = (page = 0, searchTerm = "") => {
        return API.get(`/api/v1/trade/${portfolioId}?searchTerm=${searchTerm}&page=${page}&size=10`);
    };

    const { data: trades, isLoading, isError } = useQuery(['trades', currentPage, searchTerm], () => fetchTrades(currentPage - 1, searchTerm), {
        staleTime: 5000,
    });

    const debouncedLoadTrades = useMemo(
        () => _.debounce((search) => setSearchTerm(search), 500),
        [],
    );

    return (
        <div>
            <Form.Control
                type="text"
                placeholder="Search..."
                onChange={(e) => debouncedLoadTrades(e.target.value)}
                className="my-3"
            />
            {isLoading ? (
                <Spinner animation="border" role="status">
                    <span className="sr-only">Loading...</span>
                </Spinner>
            ) : isError ? (
                <p>Error loading trades</p>
            ) : (
                <>
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
                        {trades && trades.data.content.map((trade) => (
                            <TradeRow key={trade.id} trade={trade}/>
                        ))}
                        </tbody>
                    </Table>
                    <Pagination className="justify-content-center my-4">
                        <Pagination.Prev disabled={currentPage === 1} onClick={() => setCurrentPage(currentPage - 1)}/>
                        {[...Array(trades.data.totalPages)].map((_, idx) => (
                            <Pagination.Item active={idx+1 === currentPage} key={idx+1} onClick={() => setCurrentPage(idx+1)}>
                                {idx+1}
                            </Pagination.Item>
                        ))}
                        <Pagination.Next disabled={trades && trades.data.totalPages <= currentPage} onClick={() => setCurrentPage(currentPage + 1)}/>
                    </Pagination>
                </>
            )}
        </div>
    );
};

export default PortfolioTradeList;
