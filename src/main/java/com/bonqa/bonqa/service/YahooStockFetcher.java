package com.bonqa.bonqa.service;

import com.bonqa.bonqa.model.ApiStock;
import com.bonqa.bonqa.service.ApiStockFactory;
import com.bonqa.bonqa.service.StockFetcherInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class YahooStockFetcher implements StockFetcherInterface {
    private ApiStockFactory apiStockFactory;

    @Autowired
    public YahooStockFetcher(ApiStockFactory apiStockFactory) {
        this.apiStockFactory = apiStockFactory;
    }

    @Override
    public List<ApiStock> fetchStocksFromAPI() {
        try {
            String[] arr = {"AAPL", "MSFT", "GOOG", "AMZN", "NVDA", "XOM", "TSLA", "JPM", "WMT", "META", "BAC", "KO", "PFE", "MCD", "CSCO", "DIS", "NFLX", "T", "BA", "INTC", "AMD", "GE", "F"};

            return this.apiStockFactory.createFromYahooStocks(YahooFinance.get(arr));
        } catch (IOException e){
            System.out.println("Lol");
        }

        return new ArrayList<>();
    }
}
