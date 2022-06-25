import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private final String accountNumber;
    private BigDecimal balance = BigDecimal.valueOf(0.0);

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void transfer(Account receivingAccount, BigDecimal amount) {
        if (this.balance.compareTo(amount) > 0) {
            this.balance = this.balance.subtract(amount);
            receivingAccount.balance = receivingAccount.balance.add(amount);
        }
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(0.0)) > 0) {
            this.balance = this.balance.add(amount);
        }
    }

    public void withdraw(BigDecimal amount) {
        if (this.balance.compareTo(amount) >= 0) {
            this.balance = this.balance.subtract(amount);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber) && balance.equals(account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static void main(String[] args) {
        Account account1 = new Account("12345");
        Account account2 = new Account("12345");
        account1.deposit(BigDecimal.valueOf(-100.0));
        System.out.println(account1.getBalance());
    }
}
