package bonqa.marketstock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/api/v1/stocks"})
public class MarketStockController {

  private final MarketStockRepository marketStockRepository;
  private final MarketStockUpdater marketStockUpdater;

  public MarketStockController(MarketStockRepository marketStockRepository,
                               MarketStockUpdater marketStockUpdater) {
    this.marketStockRepository = marketStockRepository;
    this.marketStockUpdater = marketStockUpdater;
  }

  @GetMapping("/")
  Iterable<MarketStock> getAllStocks() {
    return marketStockRepository.findAll();
  }

  @GetMapping("/update")
  String updateStocks() {
    marketStockUpdater.update();

    return "Stocks updated";
  }
}
