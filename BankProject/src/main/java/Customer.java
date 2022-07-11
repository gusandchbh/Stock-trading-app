import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

class Customer extends User {

    private final String fullName;
    private final LocalDate birthDate;
    private final LocalDate regDate;
    private final Gender gender;
    private final List<Account> accountList;


    public Customer(String username, String password, String fullName, LocalDate birthDate, Gender gender) {
        super(username, password);
        this.fullName = fullName.toUpperCase();
        this.birthDate = birthDate;
        this.gender = gender;
        this.regDate = LocalDate.now();
        this.accountList = new ArrayList<>();
    }


    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public long getAge(LocalDate today) {
        return ChronoUnit.YEARS.between(this.getBirthDate(), today);
    }

    public String getFullName() {
        return fullName;
    }

    public BigDecimal getBalance() {
        BigDecimal balance = BigDecimal.valueOf(0.0);
        for (Account account : this.accountList) {
            balance = balance.add(account.getBalance());
        }
        return balance;
    }

    public void addAccount(Account account) {
        if (!this.accountList.contains(account)) {
            this.accountList.add(account);
        }
    }

    public Account getAccount(String accountNumber) {
        for (Account account : this.accountList) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

        public void removeAccount (Account account){
            this.accountList.remove(account);
        }

        public List<Account> getAccountList() {
            return accountList;
        }

    public Gender getGender() {
        return gender;
    }

        public List<Transaction> filterTransactions (Transaction.Type type) {
            List<Transaction> filteredTransactions = new ArrayList<>();
            for (int i = 0; i < this.accountList.size(); i++) {
                for (Transaction transaction : this.accountList.get(i).getTransactionList()) {
                    if (transaction.getType() == type) {
                        filteredTransactions.add(transaction);
                    }
                }
            } return filteredTransactions;
            }

        // Forwarding för transfer, deposit, withdraw?


    enum Gender {
        MALE(1), FEMALE(2);

        private final int code;

        Gender(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", regDate=" + regDate +
                ", gender=" + gender +
                ", accountList=" + accountList +
                '}';
    }

    public static void main (String[]args){
            LocalDate birthDate = LocalDate.of(1993, Month.SEPTEMBER, 1);
            Customer customer = new Customer("example", "example", "Johnny Depp", birthDate, Customer.Gender.MALE);

            customer.addAccount(new Account("123"));
            customer.addAccount(new Account("345"));
            var account1 = customer.getAccount("123");
            var account2 = customer.getAccount("345");
            account1.deposit(BigDecimal.valueOf(100.00));
            account2.deposit(BigDecimal.valueOf(200.00));
            var x = customer.filterTransactions(Transaction.Type.DEPOSIT);
            System.out.println(x);
        }
    }


