package bonqa.portfoliostock;

import bonqa.marketstock.MarketStock;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PortfolioStockDTO {
    private Long id;
    private BigDecimal totalValue;
    private String stockName;
    private Integer quantity;
    private MarketStock marketStock;
}
