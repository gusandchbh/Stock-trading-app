package com.bonqa.bonqa.service;

import com.bonqa.bonqa.model.Customer;
import com.bonqa.bonqa.model.Portfolio;

public interface PortfolioService {

    Portfolio createPortfolio(Customer customer);

    void savePortfolio(Portfolio portfolio);
}
