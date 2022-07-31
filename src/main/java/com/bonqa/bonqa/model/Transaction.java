package com.bonqa.bonqa.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode
@ToString
@Entity
@Table(schema = "transaction")
@NoArgsConstructor
@Getter
public class Transaction {
    @Id
    private UUID id; // To make each transaction unique

    @Column(nullable = false)
    private BigDecimal amount; // Amount of the transaction

    @Column(name ="create_date", nullable = false)
    private LocalDate createDate; // Date of the transaction

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "id")
    private TransactionType transactionType;

    public Transaction(BigDecimal amount) {
        this.id = UUID.randomUUID(); // Generate a unique ID for each transaction
        this.createDate = LocalDate.now(); // Set the date of the transaction to the current date
        this.amount = amount; // Set the amount of the transaction
    }
}
