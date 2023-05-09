package bonqa.portfoliostock;

import bonqa.marketstock.MarketStockDTO;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioStockDTO {
  private Long id;
  private MarketStockDTO stock;

  private BigDecimal totalValue;
  private String stockName;
  private Integer quantity;
  private BigDecimal averagePurchasePrice;

}


