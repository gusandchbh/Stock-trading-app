package bonqa.portfoliostock;

import bonqa.marketstock.MarketStock;
import bonqa.portfolio.Portfolio;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@SuppressWarnings("checkstyle:MissingJavadocType")
@Entity
@Table(name = "portfolio_stock")
@Getter
@Setter
@NoArgsConstructor
public class PortfolioStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private MarketStock marketStock;

    @Column(name = "total_value")
    private BigDecimal totalValue;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "quantity")
    private Integer quantity;

}
