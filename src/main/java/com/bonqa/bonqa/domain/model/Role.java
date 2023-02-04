package com.bonqa.bonqa.domain.model;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

public enum Role {
    USER,
    ADMIN;

    @Service
    public static class PortfolioFactory {
        public Portfolio createPortfolio(User user) {
            Portfolio portfolio = new Portfolio();
            portfolio.setUser(user);
            portfolio.setStocks(new ArrayList<>());
            portfolio.setTransactionList(new ArrayList<>());
            portfolio.setTotalValue(BigDecimal.valueOf(0));

            return portfolio;
        }
    }
}
