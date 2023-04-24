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
import com.bonqa.bonqa.exception.InsufficientFundsException;
import com.bonqa.bonqa.exception.InsufficientSharesException;
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

  public List<PortfolioStock> getAllUserStocks(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    Portfolio portfolio = user.getPortfolio();
    return portfolio.getStocks();
  }

  @Transactional
  public String purchaseStock(Long userId, Long stockId, int quantity) {
    User user = findUserById(userId);
    Portfolio portfolio = user.getPortfolio();
    Stock stock = findStockById(stockId);

    BigDecimal purchasePricePerShare = stock.getPrice();
    BigDecimal totalPrice = calculateAmount(purchasePricePerShare, quantity);

    checkSufficientFunds(portfolio, totalPrice);

    PortfolioStock portfolioStock = updatePortfolioAndPurchaseTrade(portfolio, stock, quantity, purchasePricePerShare, totalPrice);

    var pricePerShare = portfolioStock.getStock().getPrice();

    return generateTransactionMessage(portfolioStock, quantity, pricePerShare, TradeType.BUY);
  }


  private void checkSufficientFunds(Portfolio portfolio, BigDecimal totalPrice) {
    if (portfolio.getAccountBalance().compareTo(totalPrice) < 0) {
      throw new InsufficientFundsException("Insufficient funds to purchase the stock");
    }
  }


  private PortfolioStock updatePortfolioAndPurchaseTrade(Portfolio portfolio, Stock stock, int quantity,
                                               BigDecimal purchasePricePerShare,
                                               BigDecimal totalPrice) {
    portfolio.setAccountBalance(portfolio.getAccountBalance().subtract(totalPrice));

    PortfolioStock portfolioStock = findOrCreatePortfolioStock(portfolio, stock);

    updatePortfolioStockForPurchase(portfolioStock, quantity, purchasePricePerShare);

    Trade trade =
        createTrade(portfolio, stock, quantity, purchasePricePerShare, totalPrice, TradeType.BUY);
    tradeRepository.save(trade);

    portfolioRepository.save(portfolio);
    portfolioStockRepository.save(portfolioStock);

    portfolioService.updateTotalValue(portfolio);
    return portfolioStock;
  }

  private PortfolioStock findOrCreatePortfolioStock(Portfolio portfolio, Stock stock) {
    return portfolio.getStocks().stream()
        .filter(ps -> ps.getStock().getId().equals(stock.getId()))
        .findFirst()
        .orElseGet(() -> {
          PortfolioStock newPortfolioStock = new PortfolioStock();
          newPortfolioStock.setPortfolio(portfolio);
          newPortfolioStock.setStock(stock);
          newPortfolioStock.setStockName(stock.getName());
          newPortfolioStock.setQuantity(0); // Initialize the quantity to 0
          newPortfolioStock.setAveragePurchasePrice(BigDecimal.ZERO); // Initialize the averagePurchasePrice to 0
          portfolio.getStocks().add(newPortfolioStock); // Add the new PortfolioStock to the Portfolio's stocks list
          return newPortfolioStock;
        });
  }

  private void updatePortfolioStockForPurchase(PortfolioStock portfolioStock, int quantity,
                                               BigDecimal purchasePricePerShare) {
    if (portfolioStock.getQuantity() == null) {
      initializeNewPortfolioStock(portfolioStock, quantity, purchasePricePerShare);
    } else {
      updateExistingPortfolioStock(portfolioStock, quantity, purchasePricePerShare);
    }
  }

  private void initializeNewPortfolioStock(PortfolioStock portfolioStock, int quantity,
                                           BigDecimal purchasePricePerShare) {
    portfolioStock.setAveragePurchasePrice(purchasePricePerShare);
    portfolioStock.setQuantity(quantity);

    BigDecimal bigDecimalQuantity = BigDecimal.valueOf(portfolioStock.getQuantity());
    BigDecimal currentValue = bigDecimalQuantity.multiply(purchasePricePerShare);
    portfolioStock.setTotalValue(currentValue);
  }

  private void updateExistingPortfolioStock(PortfolioStock portfolioStock, int quantity,
                                            BigDecimal purchasePricePerShare) {
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
    BigDecimal currentValue = bigDecimalQuantity.multiply(portfolioStock.getStock().getPrice());
    portfolioStock.setTotalValue(currentValue);
  }


  @Transactional
  public String sellStock(Long userId, Long stockId, int quantity) {
    User user = findUserById(userId);
    Portfolio portfolio = user.getPortfolio();
    Stock stock = findStockById(stockId);
    PortfolioStock portfolioStock = findPortfolioStock(portfolio, stockId);

    checkSufficientShares(quantity, portfolioStock);

    BigDecimal sellPricePerShare = stock.getPrice();
    BigDecimal amount = calculateAmount(sellPricePerShare, quantity);

    updatePortfolioAndTrade(portfolio, stock, quantity, sellPricePerShare, amount, TradeType.SELL);

    return generateTransactionMessage(portfolioStock, quantity, sellPricePerShare, TradeType.SELL);
  }

  private User findUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  private PortfolioStock findPortfolioStock(Portfolio portfolio, Long stockId) {
    return portfolio.getStocks().stream()
        .filter(ps -> ps.getStock().getId().equals(stockId))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Stock not found in the portfolio"));
  }

  private void checkSufficientShares(int quantity, PortfolioStock portfolioStock) {
    if (quantity > portfolioStock.getQuantity()) {
      throw new InsufficientSharesException("Insufficient quantity to sell the stock");
    }
  }

  private BigDecimal calculateAmount(BigDecimal sellPricePerShare, int quantity) {
    return sellPricePerShare.multiply(BigDecimal.valueOf(quantity));
  }

  private void updatePortfolioAndTrade(Portfolio portfolio, Stock stock, int quantity,
                                       BigDecimal sellPricePerShare, BigDecimal amount,
                                       TradeType tradeType) {
    portfolioService.updateAccountBalance(portfolio, amount);

    Trade trade = createTrade(portfolio, stock, quantity, sellPricePerShare, amount, tradeType);
    tradeRepository.save(trade);

    updatePortfolioStock(portfolio, stock, quantity);
  }

  private Trade createTrade(Portfolio portfolio, Stock stock, int quantity,
                            BigDecimal sellPricePerShare, BigDecimal amount, TradeType tradeType) {
    Trade trade = new Trade();
    trade.setPricePerShare(sellPricePerShare);
    trade.setShares(quantity);
    trade.setAmount(amount);
    trade.setCreateDate(LocalDateTime.now());
    trade.setTradeType(tradeType);
    trade.setPortfolio(portfolio);
    trade.setStock(stock);
    return trade;
  }

  private void updatePortfolioStock(Portfolio portfolio, Stock stock, int quantity) {
    PortfolioStock portfolioStock = findPortfolioStock(portfolio, stock.getId());
    int newQuantity = portfolioStock.getQuantity() - quantity;
    if (newQuantity == 0) {
      portfolioService.removePortfolioStock(portfolio.getId(), portfolioStock);
    } else {
      portfolioStock.setQuantity(newQuantity);
      portfolioService.updatePortfolioStockValue(portfolio.getId(), portfolioStock);
    }
  }
  private String generateTransactionMessage(PortfolioStock portfolioStock, int quantity,
                                            BigDecimal pricePerShare, TradeType tradeType) {
    String stockName = portfolioStock.getStockName();
    BigDecimal bigDecimalQuantity = BigDecimal.valueOf(quantity);
    BigDecimal totalValue = bigDecimalQuantity.multiply(pricePerShare);
    String operation = tradeType == TradeType.BUY ? "bought" : "sold";

    return String.format("You have %s %d shares of %s for %.2f USD", operation, quantity, stockName,
        totalValue.doubleValue());
  }


}