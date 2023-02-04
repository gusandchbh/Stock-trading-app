package com.bonqa.bonqa.service;

import com.bonqa.bonqa.model.ApiStock;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// models
// DTOs
// controllers
// domain services (fetcher, updater, user service)
// domain interfaces (generic fetcher, updater)
// factories
// repositories


@Component
public class ApiStockFactory {
    public ApiStock createFromYahooStock(yahoofinance.Stock stock) {
        ApiStock apiStock = new ApiStock();
        apiStock.setTicker(stock.getSymbol());
        apiStock.setName(stock.getName());
        apiStock.setPrice(stock.getQuote().getPrice());
        apiStock.setVolume(stock.getQuote().getVolume());
        apiStock.setOpen(stock.getQuote().getOpen());
        apiStock.setClose(stock.getQuote().getPreviousClose());

        return apiStock;
    }

    public List<ApiStock> createFromYahooStocks(Map<String, yahoofinance.Stock> stocks) {
        var apiStocks = new ArrayList<ApiStock>();

        for (yahoofinance.Stock stock: stocks.values()) {
            apiStocks.add(this.createFromYahooStock(stock));
        }

        return apiStocks;
    }
}
