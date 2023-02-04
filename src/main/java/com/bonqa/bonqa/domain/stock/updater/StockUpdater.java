package com.bonqa.bonqa.domain.stock.updater;
import com.bonqa.bonqa.domain.model.data.StockGeneric;
import com.bonqa.bonqa.domain.model.Stock;
import com.bonqa.bonqa.domain.repository.StockRepository;
import com.bonqa.bonqa.domain.stock.fetcher.StockFetcherInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

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
        List<StockGeneric> stocks = stockFetcher.fetchStocksFromAPI();
        this.updateStocksInDatabase(stocks);
    }

    @Scheduled(cron = "0 0 22 * * ?") // At 22:00:00pm every day
    private void automaticStockUpdate() {
      this.update();
    }

    private void updateStocksInDatabase(List<StockGeneric> stocks) {
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
