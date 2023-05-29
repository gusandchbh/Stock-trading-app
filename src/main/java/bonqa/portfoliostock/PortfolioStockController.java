package bonqa.portfoliostock;

import java.util.List;

import bonqa.user.AuthorizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{userId}")
public class PortfolioStockController {

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
}
