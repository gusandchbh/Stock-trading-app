package com.bonqa.bonqa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @JoinColumn(name="account_id", nullable=false)
    private Account account;

}
