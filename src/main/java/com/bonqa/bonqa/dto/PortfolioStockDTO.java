package com.bonqa.bonqa.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioStockDTO {
  private Long id;
  private StockDTO stock;

  private BigDecimal totalValue;
  private String stockName;
  private BigDecimal purchasePrice;
  private LocalDateTime purchaseDate;
  private Integer quantity;
  private BigDecimal currentPrice;

}


