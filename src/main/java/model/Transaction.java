package model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode
@ToString
public class Transaction implements Comparable<Transaction> {
    private final String transactionID; // To make each transaction unique
    private final LocalDate date; // Date of the transaction
    private final BigDecimal amount; // Amount of the transaction
    private final Type type; // Type of the transaction
    //private final String description; // Description of the transaction

    public Transaction(BigDecimal amount, Type type) {
        this.transactionID = UUID.randomUUID().toString(); // Generate a unique ID for each transaction
        this.date = LocalDate.now(); // Set the date of the transaction to the current date
        this.amount = amount; // Set the amount of the transaction
        this.type = type; // Set the type of the transaction
    }

        public Type getType() {
        return type;
        }

        public String getTypeString() {
        return type.toString();
        }

    @Override
    public int compareTo(Transaction transaction) {
        return transaction.amount.compareTo(this.amount);
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public enum Type {
        DEPOSIT(1), WITHDRAWAL(2), TRANSFER(3);
        private final int code;
        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
