package Models;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class Account {
    private final String accountNumber;
    private BigDecimal balance;
    private final List<Transaction> transactionList;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.transactionList = new ArrayList<>();
        this.balance = BigDecimal.valueOf(0.0);
    }

    public void transfer(Account receivingAccount, BigDecimal amount) {
        if (this.balance.compareTo(amount) > 0) {
            this.balance = this.balance.subtract(amount);
            receivingAccount.balance = receivingAccount.balance.add(amount);
            Transaction transaction = new Transaction(amount.negate(), Transaction.Type.TRANSFER);
            receivingAccount.transactionList.add(new Transaction(amount, Transaction.Type.TRANSFER));
            transactionList.add(transaction);
        }
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(0.0)) > 0) {
            this.balance = this.balance.add(amount);
            Transaction transaction = new Transaction(amount, Transaction.Type.DEPOSIT);
            transactionList.add(transaction);
        }
    }

    public void withdraw(BigDecimal amount) {
        if (this.balance.compareTo(amount) >= 0) {
            this.balance = this.balance.subtract(amount);
            Transaction transaction = new Transaction(amount.negate(), Transaction.Type.WITHDRAWAL);
            transactionList.add(transaction);
        }
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public List<Transaction> filterTransactions(Transaction.Type type) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            if (transaction.getType() == type) {
                filteredTransactions.add(transaction);
            }
        }  return filteredTransactions;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        Account account1 = new Account("12345");
        Account account2 = new Account("12345");
        account1.deposit(BigDecimal.valueOf(-100.0));
        System.out.println(account1.getBalance());
    }
}
