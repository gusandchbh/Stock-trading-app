package bonqa.marketstock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/api/v1/stocks"})
public class MarketStockController {

    private final MarketStockRepository marketStockRepository;

    public MarketStockController(MarketStockRepository marketStockRepository) {
        this.marketStockRepository = marketStockRepository;
    }

    @GetMapping("/")
    Iterable<MarketStock> getAllStocks() {
        return marketStockRepository.findAll();
    }

}
