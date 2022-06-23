public class Account {
    private final String accountNumber;
    private Double balance = 0.0;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
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
}
