package bonqa.marketstock;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/api/v1/stocks"})
public class MarketStockController {

    private final MarketStockRepository marketStockRepository;
    private final ModelMapper modelMapper;

    public MarketStockController(MarketStockRepository marketStockRepository, ModelMapper modelMapper) {
        this.marketStockRepository = marketStockRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    Iterable<MarketStock> getAllStocks() {
        return marketStockRepository.findAll();
    }

    @GetMapping("/{stockId}")
    ResponseEntity<MarketStockDTO> getStock(@PathVariable Long stockId) {
        Optional<MarketStock> marketStock = marketStockRepository.findById(stockId);
        if (marketStock.isPresent()) {
            MarketStockDTO stockDTO = modelMapper.map(marketStock.get(), MarketStockDTO.class);
            return ResponseEntity.ok(stockDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
