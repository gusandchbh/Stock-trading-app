package com.bonqa.bonqa.domain.model.data;

import com.bonqa.bonqa.domain.model.data.StockGeneric;
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
public class StockGenericFactory {
    public StockGeneric createFromYahooStock(yahoofinance.Stock stock) {
        StockGeneric stockGeneric = new StockGeneric();
        stockGeneric.setTicker(stock.getSymbol());
        stockGeneric.setName(stock.getName());
        stockGeneric.setPrice(stock.getQuote().getPrice());
        stockGeneric.setVolume(stock.getQuote().getVolume());
        stockGeneric.setOpen(stock.getQuote().getOpen());
        stockGeneric.setClose(stock.getQuote().getPreviousClose());

        return stockGeneric;
    }

    public List<StockGeneric> createFromYahooStocks(Map<String, yahoofinance.Stock> stocks) {
        var apiStocks = new ArrayList<StockGeneric>();

        for (yahoofinance.Stock stock: stocks.values()) {
            apiStocks.add(this.createFromYahooStock(stock));
        }

        return apiStocks;
    }
}