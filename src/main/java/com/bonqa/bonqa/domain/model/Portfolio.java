package com.bonqa.bonqa.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalValue;

    @Column(name = "balance", nullable = false)
    private BigDecimal accountBalance = BigDecimal.valueOf(0);

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolio")
    private List<PortfolioStock> stocks;

    @OneToMany(mappedBy="portfolio")
    private List<Transaction> transactionList;

    @OneToOne
    @JoinColumn(name="bank_user_id")
    private User user;
}
