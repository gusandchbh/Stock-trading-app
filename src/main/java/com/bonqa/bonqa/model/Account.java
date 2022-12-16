package com.bonqa.bonqa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    private UUID id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy="account")
    private List<Transaction> transactionList;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    public Account(String accountNumber) {
        this.id = UUID.randomUUID();
        this.accountNumber = accountNumber;
        this.transactionList = new ArrayList<>();
        this.balance = BigDecimal.valueOf(0.0);
    }

    public void transfer(Account receivingAccount, BigDecimal amount) {
        if (this.balance.compareTo(amount) > 0) {
            this.balance = this.balance.subtract(amount);
            receivingAccount.balance = receivingAccount.balance.add(amount);
            //@TODO: types of transactions now missing at instantiation (type = transfer)
            Transaction transaction = new Transaction(amount.negate());
            receivingAccount.transactionList.add(new Transaction(amount));
            transactionList.add(transaction);
        }
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(0.0)) > 0) {
            this.balance = this.balance.add(amount);
            //@TODO: types of transactions now missing at instantiation (type = deposit)
            Transaction transaction = new Transaction(amount);
            transactionList.add(transaction);
        }
    }

    public void withdraw(BigDecimal amount) {
        if (this.balance.compareTo(amount) >= 0) {
            this.balance = this.balance.subtract(amount);
            //@TODO: types of transactions now missing at instantiation (type = withdrawal)
            Transaction transaction = new Transaction(amount.negate());
            transactionList.add(transaction);
        }
    }
}
