package com.bonqa.bonqa.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDTO {
  private Long id;
  private String name;
  private BigDecimal price;
  private String ticker;
  private Long volume;
  private BigDecimal open;
  private BigDecimal close;

}
