package bonqa.marketstock;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
