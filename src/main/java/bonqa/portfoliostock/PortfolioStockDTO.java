package bonqa.portfoliostock;

import bonqa.marketstock.MarketStock;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioStockDTO {
    private Long id;
    private BigDecimal totalValue;
    private String stockName;
    private Integer quantity;
    private MarketStock marketStock;
}
