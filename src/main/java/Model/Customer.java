package Model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Customer extends User {
    private final String fullName;
    private final LocalDate birthDate;
    private final LocalDate regDate;
    private final Gender gender;
    private final List<Account> accountList;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public long getAge(LocalDate today) {
        return ChronoUnit.YEARS.between(this.getBirthDate(), today);
    }

    public String getFullName() {
        String firstName = this.fullName.split(" ")[0];
        String lastName = this.fullName.split(" ")[1];
        return firstName.substring(0,1).toUpperCase() + firstName.substring(1) + " " + lastName.substring(0,1).toUpperCase() + lastName.substring(1);
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
            for (Account account : this.accountList) {
                for (Transaction transaction : account.getTransactionList()) {
                    if (transaction.getType() == type) {
                        filteredTransactions.add(transaction);
                    }
                }
            }
            return filteredTransactions;
            }

    public enum Gender {
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
            var dateToday = LocalDate.now();
            var accounts = new ArrayList<Account>();

            Customer customer = new Customer("Christopher", birthDate, dateToday, Gender.MALE, accounts);
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


