package com.bonqa.bonqa.service;
import com.bonqa.bonqa.model.Stock;

import java.util.Map;

public interface StockService {

    yahoofinance.Stock findStock(String ticker);
    Map<String, yahoofinance.Stock> fetchStocksFromAPI();
    void updateStocksInDatabase(Map<String, yahoofinance.Stock> stocks);

    Iterable<Stock> getAllStocks();
}
