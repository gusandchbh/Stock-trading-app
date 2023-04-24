package com.bonqa.bonqa.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioStockDTO {
  private Long id;
  private StockDTO stock;

  private BigDecimal totalValue;
  private String stockName;
  private Integer quantity;
  private BigDecimal averagePurchasePrice;

}


