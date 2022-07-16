package Logic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Transaction implements Comparable<Transaction> {
    private final String transactionID; // To make each transaction unique
    private final LocalDate date; // Date of the transaction
    private final BigDecimal amount; // Amount of the transaction
    private final Type type; // Type of the transaction
    private final String description; // Description of the transaction
    private final BigDecimal balance; // Balance of the account after the transaction


    public Transaction(BigDecimal amount, Type type, String description, BigDecimal balance) {
        this.transactionID = UUID.randomUUID().toString(); // Generate a unique ID for each transaction
        this.date = LocalDate.now(); // Set the date of the transaction to the current date
        this.amount = amount; // Set the amount of the transaction
        this.type = type; // Set the type of the transaction
        this.description = description; // Set the description of the transaction to an empty string
        this.balance = balance; // Set the balance of the account to 0.0
    }

        public Type getType() {
        return type;
        }

        public String getTypeString() {
        return type.toString();
        }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionID.equals(that.transactionID) && date.equals(that.date) && amount.equals(that.amount) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, date, amount, type);
    }

    @Override
    public String toString() {
        return "Logic.Transaction{" +
                "transactionID='" + transactionID + '\'' +
                ", date=" + date +
                ", amount=" + amount.setScale(2) +
                ", type=" + type +
                '}';
    }

    @Override
    public int compareTo(Transaction transaction) {
        return transaction.amount.compareTo(this.amount);
    }


    public String getDescription() {
        return description;
    }

    public BigDecimal getBalance() {
        return balance;
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

    public static void main(String[] args){

    }

}
