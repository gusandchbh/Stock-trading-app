package bonqa.trade;

import bonqa.marketstock.MarketStock;
import bonqa.portfolio.Portfolio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private int shares;

    @Column
    private BigDecimal pricePerShare;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column
    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_id", nullable = false)
    private MarketStock marketStock;
}
