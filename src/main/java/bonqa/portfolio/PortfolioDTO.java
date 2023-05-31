package bonqa.portfolio;

import bonqa.portfoliostock.PortfolioStock;
import bonqa.portfoliostock.PortfolioStockDTO;
import bonqa.trade.Trade;
import bonqa.trade.TradeDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PortfolioDTO {

    private BigDecimal accountBalance;

    private List<PortfolioStockDTO> stocks;

    private List<TradeDTO> tradeList;

    private BigDecimal totalValue;

}
