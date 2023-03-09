package com.bonqa.bonqa.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

  @Column(name = "create_date", nullable = false)
  private LocalDate createDate;

  @ManyToOne
  @JoinColumn(name = "portfolio_id", nullable = false)
  private Portfolio portfolio;

  @OneToOne(cascade = CascadeType.ALL, optional = true)
  @JoinColumn(name = "trade_id", referencedColumnName = "id")
  private Trade trade;

}
