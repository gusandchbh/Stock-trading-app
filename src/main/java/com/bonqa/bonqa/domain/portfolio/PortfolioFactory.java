package com.bonqa.bonqa.domain.portfolio;

import com.bonqa.bonqa.domain.model.Portfolio;
import com.bonqa.bonqa.domain.model.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class PortfolioFactory {
  public Portfolio createPortfolio(User user) {
    Portfolio portfolio = new Portfolio();
    portfolio.setUser(user);
    portfolio.setStocks(new ArrayList<>());
    portfolio.setTransactionList(new ArrayList<>());
    portfolio.setTotalValue(BigDecimal.valueOf(0));

    return portfolio;
  }
}