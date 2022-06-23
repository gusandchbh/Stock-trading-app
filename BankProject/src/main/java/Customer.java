import Utility.LocalDateFormatter;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



class Customer extends User {


    private final String fullName;
    private final LocalDate birthDate;
    private final LocalDate regDate;
    private final Gender gender;
    private final List<Account> accountList;


    public Customer(String username, String password, String fullName, LocalDate birthDate, Gender gender) {
        super(username, password);
        this.fullName = fullName;
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

    public double getBalance() {
        double balance = 0.0;
        for (Account account : this.accountList) {
            balance += account.getBalance();
        }
        return balance;
    }

    public void addAccount(Account account) {
        if (!this.accountList.contains(account)) {
            this.accountList.add(account);
        }
    }

        public void removeAccount (Account account){
            this.accountList.remove(account);
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
            account.setBalance(200.0);
            customer.printAccountList();
            System.out.println(customer.getBalance());

        }
    }


