package bonqa.controller;

import bonqa.domain.model.Stock;
import bonqa.domain.repository.StockRepository;
import bonqa.domain.stock.updater.StockUpdater;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/api/v1/stocks"})
public class StockController {

  private final StockRepository stockRepository;
  private final StockUpdater stockUpdater;

  public StockController(StockRepository stockRepository, StockUpdater stockUpdater) {
    this.stockRepository = stockRepository;
    this.stockUpdater = stockUpdater;
  }

  @GetMapping("/")
  Iterable<Stock> getAllStocks() {
    return stockRepository.findAll();
  }

  @GetMapping("/update")
  String updateStocks() {
    stockUpdater.update();

    return "Stocks updated";
  }
}
