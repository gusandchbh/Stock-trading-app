package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/stocks"})
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

   /* @GetMapping(value = "/", params = "ticker")
    String all(@RequestParam(value = "ticker") String ticker) {
        return stockService.findStock(ticker);
    }*/

    @GetMapping("/all")
    String getAllStocks(){
        return stockService.fetchStocksFromAPI().toString();
    }


    @GetMapping("/update")
    String updateStocks(){
        var x = stockService.fetchStocksFromAPI();
        stockService.updateStocksInDatabase(x);
        return ":D";
    }
}
