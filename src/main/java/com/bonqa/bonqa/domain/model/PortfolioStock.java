package com.bonqa.bonqa.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
