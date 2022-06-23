import java.util.Objects;

public class Account {
    private final String accountNumber;
    private Double balance = 0.0;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void transfer(Account receivingAccount, double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            receivingAccount.balance += amount;
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public static void main(String[] args) {
        Account account1 = new Account("12345");
        Account account2 = new Account("12345");
        account1.deposit(-1000.0);
        System.out.println(account1.getBalance());
    }
}
