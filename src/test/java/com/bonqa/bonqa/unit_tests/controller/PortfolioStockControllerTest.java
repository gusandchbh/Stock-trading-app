package com.bonqa.bonqa.unit_tests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import bonqa.portfoliostock.PortfolioStock;
import bonqa.portfoliostock.PortfolioStockController;
import bonqa.portfoliostock.PortfolioStockDTO;
import bonqa.portfoliostock.PortfolioStockService;
import bonqa.user.AuthorizationService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class PortfolioStockControllerTest {

  @InjectMocks
  private PortfolioStockController portfolioStockController;

  @Mock
  private PortfolioStockService portfolioStockService;

  @Mock
  private AuthorizationService authorizationService;

  @Mock
  private ModelMapper modelMapper;

  @Test
  void testPurchaseStock_Authorized() {
    Long userId = 1L;
    Long stockId = 1L;
    int quantity = 10;

    when(authorizationService.isAuthenticatedUser(userId)).thenReturn(true);
    when(portfolioStockService.purchaseStock(userId, stockId, quantity)).thenReturn("Stock purchased successfully");

    ResponseEntity<String> response = portfolioStockController.purchaseStock(userId, stockId, quantity);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Stock purchased successfully", response.getBody());
  }

  @Test
  void testPurchaseStock_NotAuthorized() {
    Long userId = 1L;
    Long stockId = 1L;
    int quantity = 10;

    when(authorizationService.isAuthenticatedUser(userId)).thenReturn(false);

    ResponseEntity<String> response = portfolioStockController.purchaseStock(userId, stockId, quantity);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertEquals("You are not authorized to purchase stocks for this user", response.getBody());
  }

  @Test
  void testSellStock_Authorized() {
    Long userId = 1L;
    Long stockId = 1L;
    int quantity = 10;

    when(authorizationService.isAuthenticatedUser(userId)).thenReturn(true);
    when(portfolioStockService.sellStock(userId, stockId, quantity)).thenReturn("Stock sold successfully");

    ResponseEntity<String> response = portfolioStockController.sellStock(userId, stockId, quantity);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Stock sold successfully", response.getBody());
  }

  @Test
  void testSellStock_NotAuthorized() {
    Long userId = 1L;
    Long stockId = 1L;
    int quantity = 10;

    when(authorizationService.isAuthenticatedUser(userId)).thenReturn(false);

    ResponseEntity<String> response = portfolioStockController.sellStock(userId, stockId, quantity);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertEquals("You are not authorized to sell stocks for this user", response.getBody());
  }

  @Test
  void testGetAllUserStocks() {
    Long userId = 1L;

    PortfolioStock stock = new PortfolioStock();
    stock.setId(1L);
    stock.setId(1L);
    stock.setQuantity(10);

    PortfolioStockDTO stockDTO = new PortfolioStockDTO();
    stockDTO.setId(1L);
    stockDTO.setId(1L);
    stockDTO.setQuantity(10);

    when(portfolioStockService.getAllUserStocks(userId)).thenReturn(List.of(stock));
    when(modelMapper.map(stock, PortfolioStockDTO.class)).thenReturn(stockDTO);

    ResponseEntity<List<PortfolioStockDTO>> response = portfolioStockController.getAllUserStocks(userId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().size());
    assertEquals(stockDTO, response.getBody().get(0));
  }

  @Test
  void testSellStock_ServiceThrowsException() {
    Long userId = 1L;
    Long stockId = 1L;
    int quantity = 10;

    when(authorizationService.isAuthenticatedUser(userId)).thenReturn(true);
    when(portfolioStockService.sellStock(userId, stockId, quantity)).thenThrow(new RuntimeException("Not enough stocks to sell"));

    assertThrows(RuntimeException.class, () -> portfolioStockController.sellStock(userId, stockId, quantity));
  }

  @Test
  void testGetAllUserStocks_NoStocks() {
    Long userId = 1L;

    when(portfolioStockService.getAllUserStocks(userId)).thenReturn(new ArrayList<>());

    ResponseEntity<List<PortfolioStockDTO>> response = portfolioStockController.getAllUserStocks(userId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(Objects.requireNonNull(response.getBody()).isEmpty());
  }
}