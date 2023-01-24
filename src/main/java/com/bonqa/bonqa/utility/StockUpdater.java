package com.bonqa.bonqa.utility;

import com.bonqa.bonqa.model.Stock;
import com.bonqa.bonqa.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class StockUpdater {


    private StockService stockService;

    @Autowired
    public StockUpdater(StockService stockService){
        this.stockService = stockService;
    }

    @Scheduled(cron = "0 0 22 * * ?")
    public void updateStocks() {
        Map<String, yahoofinance.Stock> stocks = stockService.fetchStocksFromAPI();
       /* stockService.updateStocksInDatabase(stocks);*/
    }
}
