package bonqa.trade;

import bonqa.marketstock.MarketStock;
import bonqa.portfolio.Portfolio;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TradeFactory {

  public Trade createTrade(BigDecimal amount, int shares, BigDecimal pricePerShare, TradeType tradeType, Portfolio portfolio, MarketStock marketStock) {
    Trade trade = new Trade();
    trade.setAmount(amount);
    trade.setShares(shares);
    trade.setPricePerShare(pricePerShare);
    trade.setCreateDate(LocalDateTime.now());
    trade.setTradeType(tradeType);
    trade.setPortfolio(portfolio);
    trade.setMarketStock(marketStock);
    return trade;
  }
}
