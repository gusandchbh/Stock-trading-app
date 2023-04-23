package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.domain.model.Portfolio;
import com.bonqa.bonqa.domain.stock.PortfolioStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{userId}")
public class PortfolioController {

  private final PortfolioStockService portfolioStockService;

  @Autowired
  public PortfolioController(PortfolioStockService portfolioStockService) {
    this.portfolioStockService = portfolioStockService;
  }

  @GetMapping("/portfolio")
  public ResponseEntity<Portfolio> getPortfolio(@PathVariable Long userId) {
    Portfolio portfolio = portfolioStockService.findPortfolioById(userId);
    return ResponseEntity.ok(portfolio);
  }

}
