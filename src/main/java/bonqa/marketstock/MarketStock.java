package bonqa.marketstock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "stock")
@ToString
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MarketStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_name", nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "ticker", nullable = false)
    private String ticker;

    @Column(name = "volume", nullable = false)
    private Long volume;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;
}
