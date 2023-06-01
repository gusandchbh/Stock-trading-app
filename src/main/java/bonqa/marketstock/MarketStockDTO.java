package bonqa.marketstock;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MarketStockDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private String ticker;
    private Long volume;
    private LocalDateTime lastUpdated;
}
