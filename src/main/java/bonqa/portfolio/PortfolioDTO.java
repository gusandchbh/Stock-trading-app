package bonqa.portfolio;

import bonqa.portfoliostock.PortfolioStockDTO;
import bonqa.trade.TradeDTO;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioDTO {

    private Long id;

    private BigDecimal accountBalance;

    private List<PortfolioStockDTO> stocks;

    private List<TradeDTO> tradeList;

    private BigDecimal totalValue;
}
