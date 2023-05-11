package bonqa.marketstock;

import java.math.BigDecimal;
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
  private BigDecimal open;
  private BigDecimal close;

}
