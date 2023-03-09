package com.bonqa.bonqa.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Trade {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private BigDecimal totalPrice;

  @Column(nullable = false)
  private int shares;

  @Column
  private BigDecimal pricePerShare;

  @Column
  private LocalDateTime tradeTime;

  @Column
  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;

  @ManyToOne
  @JoinColumn(name = "portfolio_id")
  private Portfolio portfolio;
}
