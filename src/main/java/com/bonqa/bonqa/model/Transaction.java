package com.bonqa.bonqa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.time.LocalDate;

@ToString
@Entity
@Table(name = "transaction")
@NoArgsConstructor
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name ="create_date", nullable = false)
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name="portfolio_id", nullable=false)
    private Portfolio portfolio;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "trade_id", referencedColumnName = "id")
    private Trade trade;

}
