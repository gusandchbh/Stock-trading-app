package bonqa.marketstock.generic;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockGeneric {
    private String name;
    private BigDecimal price;
    private String ticker;
    private Long volume;
    private BigDecimal open;
    private BigDecimal close;
}
