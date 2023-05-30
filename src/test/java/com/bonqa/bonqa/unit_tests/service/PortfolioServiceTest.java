package com.bonqa.bonqa.unit_tests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
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
import org.junit.jupiter.api.BeforeEach;
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

  private Portfolio portfolio;
  private PortfolioStock portfolioStock;
  private Trade trade;

  @BeforeEach
  void setUp() {
    portfolio = new Portfolio();
    portfolio.setStocks(new ArrayList<>());

    portfolioStock = new PortfolioStock();
    portfolioStock.setQuantity(10);

    MarketStock marketStock = new MarketStock();
    marketStock.setId(1L);
    marketStock.setPrice(BigDecimal.TEN);
    portfolioStock.setMarketStock(marketStock);

    trade = new Trade();
    trade.setTradeType(TradeType.BUY);
    trade.setShares(10);
    trade.setPricePerShare(BigDecimal.TEN);
  }

  @Test
  void testUpdateTotalValue() {
    portfolio.setStocks(List.of(portfolioStock));

    portfolioService.updateTotalValue(portfolio);

    verify(portfolioRepository).save(portfolio);
    assertEquals(BigDecimal.valueOf(100), portfolio.getTotalValue());
  }

  @Test
  void testRemovePortfolioStock() {
    portfolio.setStocks(new ArrayList<>(List.of(portfolioStock)));
    when(portfolioRepository.findById(anyLong())).thenReturn(Optional.of(portfolio));

    portfolioService.removePortfolioStock(1L, portfolioStock);

    verify(portfolioRepository, times(2)).save(portfolio);
    verify(portfolioStockRepository).delete(portfolioStock);
  }

  @Test
  void testUpdatePortfolioStockValue() {
    portfolio.setStocks(new ArrayList<>(List.of(portfolioStock)));
    when(portfolioRepository.findById(anyLong())).thenReturn(Optional.of(portfolio));
    when(tradeRepository.findByPortfolioIdAndStockId(anyLong(), anyLong())).thenReturn(
        List.of(trade));

    portfolioService.updatePortfolioStockValue(1L, portfolioStock);

    verify(portfolioRepository, times(2)).save(portfolio);
  }

  @Test
  void testUpdateAccountBalance() {
    portfolio.setAccountBalance(BigDecimal.TEN);

    portfolioService.updateAccountBalance(portfolio, BigDecimal.ONE);

    verify(portfolioRepository).save(portfolio);
    assertEquals(BigDecimal.valueOf(11), portfolio.getAccountBalance());
  }

  @Test
  void testRemovePortfolioStockWithNull() {
    assertThrows(IllegalArgumentException.class, () -> portfolioService.removePortfolioStock(1L, null));
    assertThrows(IllegalArgumentException.class, () -> portfolioService.removePortfolioStock(null, portfolioStock));
  }

  @Test
  void testUpdatePortfolioStockValueWithNull() {
    assertThrows(IllegalArgumentException.class, () -> portfolioService.updatePortfolioStockValue(1L, null));
    assertThrows(IllegalArgumentException.class, () -> portfolioService.updatePortfolioStockValue(null, portfolioStock));
  }

  @Test
  void testUpdateAccountBalanceWithNull() {
    assertThrows(NullPointerException.class, () -> portfolioService.updateAccountBalance(portfolio, null));
  }

  @Test
  void testRemovePortfolioStockNonExistingPortfolio() {
    when(portfolioRepository.findById(anyLong())).thenReturn(Optional.empty());

    portfolioService.removePortfolioStock(1L, portfolioStock);

    verify(portfolioRepository, never()).save(any(Portfolio.class));
    verify(portfolioStockRepository, never()).delete(any(PortfolioStock.class));
  }

  @Test
  void testUpdatePortfolioStockValueNonExistingPortfolio() {
    when(portfolioRepository.findById(anyLong())).thenReturn(Optional.empty());

    portfolioService.updatePortfolioStockValue(1L, portfolioStock);

    verify(portfolioRepository, never()).save(any(Portfolio.class));
  }

  @Test
  void testRemoveNonExistingPortfolioStock() {
    when(portfolioRepository.findById(anyLong())).thenReturn(Optional.of(portfolio));

    PortfolioStock nonExistingStock = new PortfolioStock();
    nonExistingStock.setMarketStock(new MarketStock());
    nonExistingStock.getMarketStock().setPrice(BigDecimal.TEN);

    portfolioService.removePortfolioStock(1L, nonExistingStock);

    verify(portfolioRepository, times(0)).save(any(Portfolio.class));
    verify(portfolioStockRepository, times(0)).delete(any(PortfolioStock.class));
  }

  @Test
  void testUpdateNonExistingPortfolioStockValue() {
    when(portfolioRepository.findById(anyLong())).thenReturn(Optional.of(portfolio));

    PortfolioStock nonExistingStock = new PortfolioStock();
    nonExistingStock.setMarketStock(new MarketStock());
    nonExistingStock.getMarketStock().setPrice(BigDecimal.TEN);

    portfolioService.updatePortfolioStockValue(1L, nonExistingStock);

    verify(portfolioRepository, times(0)).save(any(Portfolio.class));
  }

  @Test
  void testUpdateAccountBalanceNegative() {
    portfolio.setAccountBalance(BigDecimal.TEN);

    BigDecimal negativeBalanceChange = BigDecimal.valueOf(-20);
    assertThrows(IllegalArgumentException.class, () -> portfolioService.updateAccountBalance(portfolio, negativeBalanceChange));
  }



}
