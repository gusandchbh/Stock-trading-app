package com.bonqa.bonqa.domain.portfolio;

import com.bonqa.bonqa.domain.model.Portfolio;
import com.bonqa.bonqa.domain.model.PortfolioStock;
import com.bonqa.bonqa.domain.repository.PortfolioRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PortfolioService {

  private final PortfolioRepository portfolioRepository;

  @Autowired
  public PortfolioService(PortfolioRepository portfolioRepository) {
    this.portfolioRepository = portfolioRepository;
  }

  @Transactional
  public void addPortfolioStock(Long portfolioId, PortfolioStock portfolioStock) {
    Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolioId);
    if (optionalPortfolio.isPresent()) {
      Portfolio portfolio = optionalPortfolio.get();
      portfolio.getStocks().add(portfolioStock);
      portfolioStock.setPortfolio(portfolio);
      updateTotalValue(portfolio, portfolioStock.getTotalValue());
      portfolioRepository.save(portfolio);
    }
  }

  public void updateTotalValue(Portfolio portfolio) {
    BigDecimal totalValue = portfolio.getStocks().stream()
        .map(PortfolioStock::getTotalValue)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    portfolio.setTotalValue(totalValue);
    portfolioRepository.save(portfolio);
  }

  @Transactional
  public void removePortfolioStock(Long portfolioId, PortfolioStock portfolioStock) {
    Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolioId);
    if (optionalPortfolio.isPresent()) {
      Portfolio portfolio = optionalPortfolio.get();
      if (portfolio.getStocks().remove(portfolioStock)) {
        updateTotalValue(portfolio, portfolioStock.getTotalValue().negate());
        portfolioRepository.save(portfolio);
      }
    }
  }

  @Transactional
  public void updatePortfolioStockValue(Long portfolioId, PortfolioStock portfolioStock,
                                        BigDecimal newValue) {
    Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolioId);
    if (optionalPortfolio.isPresent()) {
      Portfolio portfolio = optionalPortfolio.get();
      if (portfolio.getStocks().contains(portfolioStock)) {
        BigDecimal oldValue = portfolioStock.getTotalValue();
        BigDecimal newTotalValue = newValue.multiply(BigDecimal.valueOf(portfolioStock.getQuantity()));
        portfolioStock.setTotalValue(newTotalValue);
        updateTotalValue(portfolio, newTotalValue.subtract(oldValue));
        portfolioRepository.save(portfolio);
      }
    }
  }

  public void updateAccountBalance(Portfolio portfolio, BigDecimal balanceChange) {
    BigDecimal newBalance = portfolio.getAccountBalance().add(balanceChange);
    portfolio.setAccountBalance(newBalance);
    portfolioRepository.save(portfolio);
  }

  private void updateTotalValue(Portfolio portfolio, BigDecimal stockValueChange) {
    BigDecimal newTotalValue = portfolio.getTotalValue().add(stockValueChange);
    portfolio.setTotalValue(newTotalValue);
    portfolioRepository.save(portfolio);
  }
}



