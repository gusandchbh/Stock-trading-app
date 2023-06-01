package bonqa.portfoliostock;

import bonqa.user.AuthorizationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/{userId}")
public class PortfolioStockController {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioStockController.class);

    private final PortfolioStockService portfolioStockService;
    private final ModelMapper modelMapper;
    private final AuthorizationService authorizationService;

    @Autowired
    public PortfolioStockController(PortfolioStockService portfolioStockService, ModelMapper modelMapper, AuthorizationService authorizationService) {
        this.portfolioStockService = portfolioStockService;
        this.modelMapper = modelMapper;
        this.authorizationService = authorizationService;
    }


    @PostMapping("/stocks/{stockId}/purchase")
    public ResponseEntity<String> purchaseStock(
            @PathVariable Long userId, @PathVariable Long stockId, @RequestParam int quantity) {
        if (authorizationService.isAuthenticatedUser(userId)) {
            var message = portfolioStockService.purchaseStock(userId, stockId, quantity);
            return ResponseEntity.ok(message);
        }
        return new ResponseEntity<>("You are not authorized to purchase stocks for this user", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/stocks/{stockId}/sell")
    public ResponseEntity<String> sellStock(
            @PathVariable Long userId, @PathVariable Long stockId, @RequestParam int quantity) {
        if (authorizationService.isAuthenticatedUser(userId)) {
            var message = portfolioStockService.sellStock(userId, stockId, quantity);
            return ResponseEntity.ok(message);
        }
        return new ResponseEntity<>("You are not authorized to sell stocks for this user", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/stocks")
    public ResponseEntity<List<PortfolioStockDTO>> getAllUserStocks(@PathVariable Long userId) {
        List<PortfolioStock> stocks = portfolioStockService.getAllUserStocks(userId);
        List<PortfolioStockDTO> stockDTOs = stocks.stream()
                .map(stock -> modelMapper.map(stock, PortfolioStockDTO.class))
                .toList();
        return ResponseEntity.ok(stockDTOs);
    }

    @GetMapping("/stocks/{stockId}")
    public ResponseEntity<PortfolioStockDTO> getStockByName(@PathVariable Long userId, @PathVariable Long stockId) {
        if (authorizationService.isAuthenticatedUser(userId)) {
            Optional<PortfolioStock> stock = portfolioStockService.getPortfolioStock(userId, stockId);
            if (stock.isPresent()) {
                PortfolioStockDTO stockDTO = modelMapper.map(stock.get(), PortfolioStockDTO.class);
                return ResponseEntity.ok(stockDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }


}
