package com.bonqa.bonqa.domain.stock;

import com.bonqa.bonqa.domain.model.Portfolio;
import com.bonqa.bonqa.domain.model.PortfolioStock;
import com.bonqa.bonqa.domain.model.Stock;
import com.bonqa.bonqa.domain.model.User;
import com.bonqa.bonqa.domain.portfolio.PortfolioService;
import com.bonqa.bonqa.domain.repository.PortfolioRepository;
import com.bonqa.bonqa.domain.repository.StockRepository;
import com.bonqa.bonqa.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PortfolioStockService {

  private final UserRepository userRepository;
  private final StockRepository stockRepository;
  private final PortfolioRepository portfolioRepository;
  private final PortfolioService portfolioService;

  @Autowired
  public PortfolioStockService(StockRepository stockRepository,
                               PortfolioRepository portfolioRepository,
                               UserRepository userRepository,
                               PortfolioService portfolioService) {
    this.userRepository = userRepository;
    this.stockRepository = stockRepository;
    this.portfolioRepository = portfolioRepository;
    this.portfolioService = portfolioService;
  }

  public Stock findStockById(Long stockId) {
    return stockRepository.findById(stockId)
        .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
  }

  public Portfolio findPortfolioById(Long portfolioId) {
    return portfolioRepository.findById(portfolioId)
        .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
  }

  public List<PortfolioStock> getAllUserStocks(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    Portfolio portfolio = user.getPortfolio();
    return portfolio.getStocks();
  }

  @Transactional
  public void purchaseStock(Long userId, Long stockId, int quantity) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    Portfolio portfolio = user.getPortfolio();
    Stock stock = findStockById(stockId);

    BigDecimal totalPrice = stock.getPrice().multiply(BigDecimal.valueOf(quantity));

    if (portfolio.getAccountBalance().compareTo(totalPrice) < 0) {
      throw new RuntimeException("Insufficient funds to purchase the stock");
    }

    portfolio.setAccountBalance(portfolio.getAccountBalance().subtract(totalPrice));

    PortfolioStock portfolioStock = portfolio.getStocks().stream()
        .filter(ps -> ps.getStock().getId().equals(stockId))
        .findFirst()
        .orElse(null);

    if (portfolioStock == null) {
      portfolioStock = new PortfolioStock();
      portfolioStock.setPortfolio(portfolio);
      portfolioStock.setStock(stock);
      portfolioStock.setTotalValue(BigDecimal.ZERO);
      portfolioStock.setPurchasePrice(stock.getPrice());
      portfolioStock.setPurchaseDate(LocalDateTime.now());
      portfolioStock.setQuantity(quantity);
      portfolioStock.setStockName(stock.getName());
      portfolio.getStocks().add(portfolioStock);
    } else {
      BigDecimal oldTotalValue = portfolioStock.getTotalValue();
      BigDecimal newTotalValue = oldTotalValue.add(totalPrice);
      BigDecimal newQuantity =
          BigDecimal.valueOf(portfolioStock.getQuantity()).add(BigDecimal.valueOf(quantity));
      portfolioStock.setTotalValue(newTotalValue);
      portfolioStock.setQuantity(newQuantity.intValue());
    }

    portfolioStock.setCurrentPrice(stock.getPrice());

    portfolioRepository.save(portfolio);
    portfolioService.updateTotalValue(portfolio);
  }



  @Transactional
  public void sellStock(Long userId, Long stockId, int quantity) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    Portfolio portfolio = user.getPortfolio();
    Stock stock = findStockById(stockId);

    PortfolioStock portfolioStock = portfolio.getStocks().stream()
        .filter(ps -> ps.getStock().getId().equals(stockId))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Stock not found in the portfolio"));

    if (quantity > portfolioStock.getQuantity()) {
      throw new RuntimeException("Insufficient quantity to sell the stock");
    }

    BigDecimal totalPrice = stock.getPrice().multiply(BigDecimal.valueOf(quantity));
    portfolioService.updateAccountBalance(portfolio, totalPrice);

    if (quantity == portfolioStock.getQuantity()) {
      portfolioService.removePortfolioStock(portfolio.getId(), portfolioStock);
    } else {
      BigDecimal oldTotalValue = portfolioStock.getTotalValue();
      BigDecimal newTotalValue = oldTotalValue.subtract(totalPrice);
      BigDecimal newQuantity =
          BigDecimal.valueOf(portfolioStock.getQuantity()).subtract(BigDecimal.valueOf(quantity));
      portfolioStock.setTotalValue(newTotalValue);
      portfolioStock.setQuantity(newQuantity.intValue());

      portfolioService.updatePortfolioStockValue(portfolio.getId(), portfolioStock, newTotalValue);
    }

  }
}

