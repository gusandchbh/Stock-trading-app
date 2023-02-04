package com.bonqa.bonqa.utility;
import com.bonqa.bonqa.model.ApiStock;
import com.bonqa.bonqa.model.Stock;
import com.bonqa.bonqa.repository.StockRepository;
import com.bonqa.bonqa.service.StockFetcherInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class StockUpdater {


    private final StockFetcherInterface stockFetcher;
    private final StockRepository stockRepository;
    @Autowired
    public StockUpdater(StockFetcherInterface stockFetcher, StockRepository stockRepository){
        this.stockFetcher = stockFetcher;
        this.stockRepository = stockRepository;
    }

    public void update() {
        List<ApiStock> stocks = stockFetcher.fetchStocksFromAPI();
        this.updateStocksInDatabase(stocks);
    }

    @Scheduled(cron = "0 0 22 * * ?") // At 22:00:00pm every day
    private void automaticStockUpdate() {
      this.update();
    }

    private void updateStocksInDatabase(List<ApiStock> stocks) {
        for (var stock : stocks) {
            Stock stockToSave = new Stock();
            stockToSave.setTicker(stock.getTicker());
            stockToSave.setName(stock.getName());
            stockToSave.setPrice(stock.getPrice());
            stockToSave.setVolume(stock.getVolume());
            stockToSave.setOpen(stock.getOpen());
            stockToSave.setClose(stock.getClose());
            stockRepository.save(stockToSave);
        }
    }
}
