import Models.Account;
import Models.Customer;
import Models.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TransactionTest {
    LocalDate birthday = LocalDate.of(1993, Month.SEPTEMBER, 1);
    LocalDate birthday2 = LocalDate.of(1990, Month.SEPTEMBER, 2);
    LocalDate dateToday = LocalDate.now();
    ArrayList<Account> accounts = new ArrayList<Account>();
    Customer customer1 = new Customer("Christopher", birthday, dateToday, Customer.Gender.MALE, accounts);
    Customer customer2 = new Customer("Miranda", birthday2, dateToday, Customer.Gender.FEMALE, accounts);
    Account account1 = new Account("1234");
    Account account2 = new Account("4321");

    @Test
    public void shouldOnlyAddDeposits(){
        customer1.addAccount(account1);
        customer2.addAccount(account2);
        customer1.getAccount("1234").deposit(BigDecimal.valueOf(100.0));
        var transactionList = customer1.getAccount("1234").filterTransactions(Transaction.Type.DEPOSIT);
        assertEquals(1, transactionList.size());
    }

    @Test
    public void shouldOnlyAddWithdrawals(){
        customer1.addAccount(account1);
        customer2.addAccount(account2);
        customer1.getAccount("1234").deposit(BigDecimal.valueOf(100.0));
        var transactionList = customer1.getAccount("1234").filterTransactions(Transaction.Type.WITHDRAWAL);
        assertNotEquals(1, transactionList.size());
    }
}
