package com.bonqa.bonqa.service.impl;

import com.bonqa.bonqa.model.Customer;
import com.bonqa.bonqa.model.Portfolio;
import com.bonqa.bonqa.repository.PortfolioRepository;
import com.bonqa.bonqa.service.CustomerService;
import com.bonqa.bonqa.service.PortfolioService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository){
        this.portfolioRepository = portfolioRepository;
    }


    @Override
    public Portfolio createPortfolio(Customer customer) {
        Portfolio portfolio = new Portfolio();
        portfolio.setCustomer(customer);
        portfolio.setStocks(new ArrayList<>());
        portfolio.setTrades(new ArrayList<>());
        portfolio.setTotalValue(BigDecimal.valueOf(0));
        return portfolio;
    }

    @Override
    public void savePortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }
}
