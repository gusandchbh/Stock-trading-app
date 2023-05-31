package bonqa.trade;

import bonqa.marketstock.MarketStock;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TradeDTO {

    private BigDecimal amount;
    private int shares;
    private BigDecimal pricePerShare;
    private LocalDateTime createDate;
    private TradeType tradeType;
    private MarketStock marketStock;
}
