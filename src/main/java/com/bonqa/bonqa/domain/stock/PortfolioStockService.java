package com.bonqa.bonqa.domain.stock;

import com.bonqa.bonqa.domain.model.Portfolio;
import com.bonqa.bonqa.domain.model.PortfolioStock;
import com.bonqa.bonqa.domain.model.Stock;
import com.bonqa.bonqa.domain.model.Trade;
import com.bonqa.bonqa.domain.model.TradeType;
import com.bonqa.bonqa.domain.model.User;
import com.bonqa.bonqa.domain.portfolio.PortfolioService;
import com.bonqa.bonqa.domain.repository.PortfolioRepository;
import com.bonqa.bonqa.domain.repository.PortfolioStockRepository;
import com.bonqa.bonqa.domain.repository.StockRepository;
import com.bonqa.bonqa.domain.repository.TradeRepository;
import com.bonqa.bonqa.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
  private final PortfolioStockRepository portfolioStockRepository;

  private final TradeRepository tradeRepository;

  @Autowired
  public PortfolioStockService(StockRepository stockRepository,
                               PortfolioRepository portfolioRepository,
                               UserRepository userRepository,
                               PortfolioService portfolioService,
                               PortfolioStockRepository portfolioStockRepository,
                               TradeRepository tradeRepository) {
    this.userRepository = userRepository;
    this.stockRepository = stockRepository;
    this.portfolioRepository = portfolioRepository;
    this.portfolioService = portfolioService;
    this.portfolioStockRepository = portfolioStockRepository;
    this.tradeRepository = tradeRepository;
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

    BigDecimal purchasePricePerShare = stock.getPrice();
    BigDecimal totalPrice = purchasePricePerShare.multiply(BigDecimal.valueOf(quantity));

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
      portfolioStock.setAveragePurchasePrice(purchasePricePerShare);
      portfolioStock.setCurrentPrice(stock.getPrice());
      portfolioStock.setQuantity(quantity);
      portfolioStock.setStockName(stock.getName());
      BigDecimal bigDecimalQuantity = BigDecimal.valueOf(portfolioStock.getQuantity());
      BigDecimal price = portfolioStock.getCurrentPrice();
      BigDecimal currentValue = bigDecimalQuantity.multiply(price);
      portfolioStock.setTotalValue(currentValue);
      //portfolio.getStocks().add(portfolioStock);
    } else {
      int oldQuantity = portfolioStock.getQuantity();
      BigDecimal oldAveragePurchasePrice = portfolioStock.getAveragePurchasePrice();

      int newQuantity = oldQuantity + quantity;
      BigDecimal newAveragePurchasePrice =
          (oldAveragePurchasePrice.multiply(BigDecimal.valueOf(oldQuantity))
              .add(purchasePricePerShare.multiply(BigDecimal.valueOf(quantity))))
              .divide(BigDecimal.valueOf(newQuantity), 2, RoundingMode.HALF_UP);

      portfolioStock.setQuantity(newQuantity);
      portfolioStock.setAveragePurchasePrice(newAveragePurchasePrice);
      BigDecimal bigDecimalQuantity = BigDecimal.valueOf(portfolioStock.getQuantity());
      BigDecimal price = portfolioStock.getCurrentPrice();
      BigDecimal currentValue = bigDecimalQuantity.multiply(price);
      portfolioStock.setTotalValue(currentValue);
    }

    Trade trade = new Trade();
    trade.setPricePerShare(purchasePricePerShare);
    trade.setShares(quantity);
    trade.setAmount(totalPrice);
    trade.setCreateDate(LocalDateTime.now());
    trade.setTradeType(TradeType.BUY);
    trade.setPortfolio(portfolio);
    trade.setStock(stock);
    tradeRepository.save(trade);

    portfolioRepository.save(portfolio);
    portfolioStockRepository.save(portfolioStock);

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

    BigDecimal sellPricePerShare = stock.getPrice();
    BigDecimal amount = sellPricePerShare.multiply(BigDecimal.valueOf(quantity));
    portfolioService.updateAccountBalance(portfolio, amount);

    Trade trade = new Trade();
    trade.setPricePerShare(sellPricePerShare);
    trade.setShares(quantity);
    trade.setAmount(amount);
    trade.setCreateDate(LocalDateTime.now());
    trade.setTradeType(TradeType.SELL);
    trade.setPortfolio(portfolio);
    trade.setStock(stock);
    tradeRepository.save(trade);

    if (quantity == portfolioStock.getQuantity()) {
      portfolioService.removePortfolioStock(portfolio.getId(), portfolioStock);
    } else {
      int newQuantity = portfolioStock.getQuantity() - quantity;
      portfolioStock.setQuantity(newQuantity);
      portfolioService.updatePortfolioStockValue(portfolio.getId(), portfolioStock);
    }
  }


}

