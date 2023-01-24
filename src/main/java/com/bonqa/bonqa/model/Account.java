package com.bonqa.bonqa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true, nullable = false)
    private Long accountNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.valueOf(0);

    @OneToMany(mappedBy="account")
    private List<Transaction> transactionList;

    @OneToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;
}
