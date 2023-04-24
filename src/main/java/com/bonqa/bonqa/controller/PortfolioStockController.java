package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.domain.model.PortfolioStock;
import com.bonqa.bonqa.domain.stock.PortfolioStockService;
import com.bonqa.bonqa.dto.PortfolioStockDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  public PortfolioStockController(PortfolioStockService portfolioStockService,
                                  ModelMapper modelMapper) {
    this.portfolioStockService = portfolioStockService;
    this.modelMapper = modelMapper;
  }

  @PostMapping("/stocks/{stockId}/purchase")
  public ResponseEntity<String> purchaseStock(@PathVariable Long userId, @PathVariable Long stockId,
                                            @RequestParam int quantity) {
    var message = portfolioStockService.purchaseStock(userId, stockId, quantity);
    return ResponseEntity.ok(message);
  }

  @PostMapping("/stocks/{stockId}/sell")
  public ResponseEntity<String> sellStock(@PathVariable Long userId, @PathVariable Long stockId,
                                          @RequestParam int quantity) {
    var message = portfolioStockService.sellStock(userId, stockId, quantity);
    return ResponseEntity.ok(message);
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

