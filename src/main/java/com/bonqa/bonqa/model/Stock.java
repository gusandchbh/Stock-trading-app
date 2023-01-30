package com.bonqa.bonqa.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "stock")
@ToString
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Stock {


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

    @Column(name = "open", nullable = false)
    private BigDecimal open;

    @Column(name = "close", nullable = false)
    private BigDecimal close;

}
