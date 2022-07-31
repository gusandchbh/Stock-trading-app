package com.bonqa.bonqa.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
@Table(schema = "user")
@NoArgsConstructor
@Getter
public class Customer extends User {
    @Id
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name ="birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name ="create_date", nullable = false)
    private LocalDate createDate;

    @Column(nullable = false)
    private Gender gender;

    @OneToMany(mappedBy="customer")
    private List<Account> accountList;

    public long getAge(LocalDate today) {
        return ChronoUnit.YEARS.between(this.getBirthDate(), today);
    }

    public String getFullName() {
       return getFirstName() + ' ' + getLastName();
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

    public void removeAccount(Account account) {
            this.accountList.remove(account);
        }

//    public List<Transaction> filterTransactions(Transaction.Type type) {
//        List<Transaction> filteredTransactions = new ArrayList<>();
//        for (Account account : this.accountList) {
//            for (Transaction transaction : account.getTransactionList()) {
//                if (transaction.getType() == type) {
//                    filteredTransactions.add(transaction);
//                }
//            }
//        }
//        return filteredTransactions;
//    }

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
}


