package com.bonqa.bonqa.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "portfolio_stock")
@Getter
@Setter
@NoArgsConstructor
public class PortfolioStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @OneToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private BigDecimal value;


    // Price

    // Shares

}
