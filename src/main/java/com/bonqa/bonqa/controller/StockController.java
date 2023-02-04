package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.domain.model.Stock;
import com.bonqa.bonqa.domain.repository.StockRepository;
import com.bonqa.bonqa.domain.stock.fetcher.StockFetcherInterface;
import com.bonqa.bonqa.domain.stock.updater.StockUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/stocks"})
public class StockController {

    private final StockFetcherInterface stockFetcher;
    private final StockUpdater stockUpdater;
    private final StockRepository stockRepository;

    @Autowired
    public StockController(StockFetcherInterface stockFetcher, StockUpdater stockUpdater, StockRepository stockRepository) {
        this.stockFetcher = stockFetcher;
        this.stockUpdater = stockUpdater;
        this.stockRepository = stockRepository;
    }

    @GetMapping("/")
    Iterable<Stock> getAllStocks(){
        return stockRepository.findAll();
    }

    @GetMapping("/update")
    String updateStocks(){
        stockUpdater.update();

        return "Stocks updated";
    }
}
