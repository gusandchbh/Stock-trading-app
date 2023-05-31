package bonqa.portfoliostock;

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
}
