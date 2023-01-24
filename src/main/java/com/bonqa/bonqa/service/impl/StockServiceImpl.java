package com.bonqa.bonqa.service.impl;

import com.bonqa.bonqa.model.Stock;
import com.bonqa.bonqa.repository.StockRepository;
import com.bonqa.bonqa.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }
    public yahoofinance.Stock findStock(final String ticker){
        try {
            return YahooFinance.get(ticker);
        } catch (IOException e){
            System.out.println("Error");
        }
        return null;
    }

    @Override
    public Map<String, yahoofinance.Stock> fetchStocksFromAPI() {
        try {
            String[] arr = {"AAPL", "MSFT", "GOOG", "AMZN", "NVDA", "XOM", "TSLA", "JPM", "WMT", "META", "BAC", "KO", "PFE", "MCD", "CSCO", "DIS", "NFLX", "T", "BA", "INTC", "AMD", "GE", "F"};
            return YahooFinance.get(arr);
        } catch (IOException e){
            System.out.println("Lol");
        }
        return Collections.emptyMap();
    }

    @Override
    public void updateStocksInDatabase(Map<String, yahoofinance.Stock> stocks) {
        for (Map.Entry<String, yahoofinance.Stock> entry : stocks.entrySet()) {
            String ticker = entry.getKey();
            yahoofinance.Stock stock = entry.getValue();
            Stock dbStock = stockRepository.findOneByTicker(ticker);
            if (dbStock != null) {
                dbStock.setPrice(stock.getQuote().getPrice());
                dbStock.setVolume(stock.getQuote().getVolume());
                dbStock.setOpen(stock.getQuote().getOpen());
                dbStock.setClose(stock.getQuote().getPreviousClose());
                stockRepository.save(dbStock);
            } else {
                Stock newStock = new Stock();
                newStock.setTicker(ticker);
                newStock.setName(stock.getName());
                newStock.setPrice(stock.getQuote().getPrice());
                newStock.setVolume(stock.getQuote().getVolume());
                newStock.setOpen(stock.getQuote().getOpen());
                newStock.setClose(stock.getQuote().getPreviousClose());
                stockRepository.save(newStock);
            }
        }
    }
}
