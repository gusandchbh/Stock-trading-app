package com.bonqa.bonqa.domain.portfolio;

import com.bonqa.bonqa.domain.model.Portfolio;
import com.bonqa.bonqa.domain.model.PortfolioStock;
import com.bonqa.bonqa.domain.model.Trade;
import com.bonqa.bonqa.domain.model.TradeType;
import com.bonqa.bonqa.domain.repository.PortfolioRepository;
import com.bonqa.bonqa.domain.repository.TradeRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PortfolioService {

  private final PortfolioRepository portfolioRepository;
  private final TradeRepository tradeRepository;

  @Autowired
  public PortfolioService(PortfolioRepository portfolioRepository,
                          TradeRepository tradeRepository) {
    this.portfolioRepository = portfolioRepository;
    this.tradeRepository = tradeRepository;
  }

  @Transactional
  public void addPortfolioStock(Long portfolioId, PortfolioStock portfolioStock) {
    Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolioId);
    if (optionalPortfolio.isPresent()) {
      Portfolio portfolio = optionalPortfolio.get();
      portfolio.getStocks().add(portfolioStock);
      portfolioStock.setPortfolio(portfolio);
      updateTotalValue(portfolio);
      portfolioRepository.save(portfolio);
    }
  }

  public void updateTotalValue(Portfolio portfolio) {
    BigDecimal newTotalValue = portfolio.getStocks().stream()
        .map(portfolioStock -> portfolioStock.getCurrentPrice()
            .multiply(BigDecimal.valueOf(portfolioStock.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    portfolio.setTotalValue(newTotalValue);
    portfolioRepository.save(portfolio);
  }


  @Transactional
  public void removePortfolioStock(Long portfolioId, PortfolioStock portfolioStock) {
    Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolioId);
    if (optionalPortfolio.isPresent()) {
      Portfolio portfolio = optionalPortfolio.get();
      if (portfolio.getStocks().remove(portfolioStock)) {
        updateTotalValue(portfolio);
        portfolioRepository.save(portfolio);
      }
    }
  }

  @Transactional
  public void updatePortfolioStockValue(Long portfolioId, PortfolioStock portfolioStock) {
    Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolioId);
    if (optionalPortfolio.isPresent()) {
      Portfolio portfolio = optionalPortfolio.get();
      if (portfolio.getStocks().contains(portfolioStock)) {
        List<Trade> trades = tradeRepository.findByPortfolioIdAndStockId(portfolioId,
            portfolioStock.getStock().getId());
        BigDecimal totalPurchasePrice = BigDecimal.ZERO;
        int totalShares = 0;

        for (Trade trade : trades) {
          if (trade.getTradeType() == TradeType.BUY) {
            totalPurchasePrice = totalPurchasePrice.add(
                trade.getPricePerShare().multiply(BigDecimal.valueOf(trade.getShares())));
            totalShares += trade.getShares();
          }
        }

        BigDecimal averagePurchasePrice = totalShares == 0 ? BigDecimal.ZERO :
            totalPurchasePrice.divide(BigDecimal.valueOf(totalShares), RoundingMode.HALF_UP);
        BigDecimal currentValue =
            averagePurchasePrice.multiply(BigDecimal.valueOf(portfolioStock.getQuantity()));
        portfolioStock.setTotalValue(currentValue);
        updateTotalValue(portfolio);
        portfolioRepository.save(portfolio);
      }
    }
  }

  public void updateAccountBalance(Portfolio portfolio, BigDecimal balanceChange) {
    BigDecimal newBalance = portfolio.getAccountBalance().add(balanceChange);
    portfolio.setAccountBalance(newBalance);
    portfolioRepository.save(portfolio);
  }


}



