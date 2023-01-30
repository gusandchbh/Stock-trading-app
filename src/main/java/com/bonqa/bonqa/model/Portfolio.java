package com.bonqa.bonqa.model;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<Stock> stocks;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Trade> trades;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
}
