package bonqa.marketstock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

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
