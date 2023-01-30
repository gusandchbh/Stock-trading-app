package com.bonqa.bonqa.utility;
import com.bonqa.bonqa.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class StockUpdater {


    private final StockService stockService;

    @Autowired
    public StockUpdater(StockService stockService){
        this.stockService = stockService;
    }

    @Scheduled(cron = "0 0 22 * * ?") // At 22:00:00pm every day
    public void updateStocks() {
        Map<String, yahoofinance.Stock> stocks = stockService.fetchStocksFromAPI();
       stockService.updateStocksInDatabase(stocks);
    }
}
