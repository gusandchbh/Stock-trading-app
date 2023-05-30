package com.bonqa.bonqa.unit_tests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bonqa.marketstock.MarketStock;
import bonqa.portfolio.Portfolio;
import bonqa.portfolio.PortfolioRepository;
import bonqa.portfolio.PortfolioService;
import bonqa.portfoliostock.PortfolioStock;
import bonqa.portfoliostock.PortfolioStockRepository;
import bonqa.trade.Trade;
import bonqa.trade.TradeRepository;
import bonqa.trade.TradeType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

  @InjectMocks
  private PortfolioService portfolioService;

  @Mock
  private PortfolioRepository portfolioRepository;

  @Mock
  private TradeRepository tradeRepository;

  @Mock
  private PortfolioStockRepository portfolioStockRepository;

  @Test
  void testUpdateTotalValue() {
    Portfolio portfolio = new Portfolio();
    PortfolioStock portfolioStock = new PortfolioStock();
    portfolioStock.setQuantity(10);
    portfolioStock.setMarketStock(new MarketStock());
    portfolioStock.getMarketStock().setPrice(BigDecimal.TEN);
    portfolio.setStocks(List.of(portfolioStock));

    portfolioService.updateTotalValue(portfolio);

    verify(portfolioRepository).save(portfolio);
    assertEquals(BigDecimal.valueOf(100), portfolio.getTotalValue());
  }

  @Test
  void testRemovePortfolioStock() {
    Portfolio portfolio = new Portfolio();
    PortfolioStock portfolioStock = new PortfolioStock();
    portfolio.setStocks(new ArrayList<>(List.of(portfolioStock)));
    when(portfolioRepository.findById(anyLong())).thenReturn(Optional.of(portfolio));

    portfolioService.removePortfolioStock(1L, portfolioStock);

    verify(portfolioRepository, times(2)).save(portfolio);
    verify(portfolioStockRepository).delete(portfolioStock);
  }


/*
  @Test
  void testUpdatePortfolioStockValue() {
    Portfolio portfolio = new Portfolio();
    PortfolioStock portfolioStock = new PortfolioStock();
    MarketStock marketStock = new MarketStock();
    marketStock.setId(1L);
    portfolioStock.setMarketStock(marketStock);
    portfolioStock.setQuantity(10);
    portfolio.setStocks(new ArrayList<>(List.of(portfolioStock)));
    when(portfolioRepository.findById(anyLong())).thenReturn(Optional.of(portfolio));

    Trade trade = new Trade();
    trade.setTradeType(TradeType.BUY);
    trade.setShares(10);
    trade.setPricePerShare(BigDecimal.TEN);
    when(tradeRepository.findByPortfolioIdAndStockId(anyLong(), anyLong())).thenReturn(
        List.of(trade));

    portfolioService.updatePortfolioStockValue(1L, portfolioStock);

    verify(portfolioRepository, times(2)).save(portfolio);
  }
*/



  @Test
  void testUpdateAccountBalance() {
    Portfolio portfolio = new Portfolio();
    portfolio.setAccountBalance(BigDecimal.TEN);

    portfolioService.updateAccountBalance(portfolio, BigDecimal.ONE);

    verify(portfolioRepository).save(portfolio);
    assertEquals(BigDecimal.valueOf(11), portfolio.getAccountBalance());
  }
}