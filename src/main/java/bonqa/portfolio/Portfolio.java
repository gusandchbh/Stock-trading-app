package bonqa.portfolio;

import bonqa.portfoliostock.PortfolioStock;
import bonqa.trade.Trade;
import bonqa.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "portfolio")
@Entity
@ToString
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance", nullable = false)
    private BigDecimal accountBalance = BigDecimal.valueOf(0);

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolio")
    private List<PortfolioStock> stocks;

    @JsonManagedReference
    @OneToMany(mappedBy = "portfolio")
    private List<Trade> tradeList;

    @OneToOne
    @JoinColumn(name = "bank_user_id")
    private User user;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue = BigDecimal.valueOf(0);
}
