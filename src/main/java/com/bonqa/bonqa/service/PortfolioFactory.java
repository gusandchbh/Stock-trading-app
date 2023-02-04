package com.bonqa.bonqa.service;

import com.bonqa.bonqa.model.Portfolio;
import com.bonqa.bonqa.model.User;
import com.bonqa.bonqa.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

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
