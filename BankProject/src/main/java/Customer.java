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
    private final List<Transaction> transactionList;


    public Customer(String username, String password, String fullName, LocalDate birthDate, Gender gender) {
        super(username, password);
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.regDate = LocalDate.now();
        this.accountList = new ArrayList<>();
        this.transactionList = new ArrayList<>();
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

    public void printAccountList () {
            for (Account account : this.accountList) {
                System.out.println(account.getAccountNumber());
            }
        }



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


        public static void main (String[]args){
            LocalDate birthDate = LocalDate.of(1993, Month.SEPTEMBER, 1);
            Customer customer = new Customer("example", "example", "Johnny Depp", birthDate, Customer.Gender.MALE);
            System.out.println(customer.gender);

            Account account = new Account("231930139");
            customer.accountList.add(account);
            customer.addAccount(account);
            System.out.println(account.getBalance());
            account.setBalance(BigDecimal.valueOf(1000.0));
            customer.printAccountList();
            System.out.println(customer.getBalance());

        }
    }


