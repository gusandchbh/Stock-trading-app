import Model.Account;
import Model.Customer;
import Model.Transaction;
import Model.User;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CustomerTest {
    LocalDate birthday = LocalDate.of(1993, Month.SEPTEMBER, 1);
    LocalDate birthday2 = LocalDate.of(1990, Month.SEPTEMBER, 2);
    LocalDate dateToday = LocalDate.now();
    ArrayList<Account> accounts = new ArrayList<Account>();
    Customer customer = new Customer("Christopher", birthday, dateToday, Customer.Gender.MALE, accounts);
    Customer customer1 = new Customer("Miranda", birthday2, dateToday, Customer.Gender.FEMALE, accounts);
    Customer customer2 = new Customer("Johnny Depp", birthday, dateToday, Customer.Gender.MALE, accounts);

    @Test
    public void shouldBeTheSameAge() {
        assertEquals(customer.getAge(birthday), customer2.getAge(birthday));
    }
    @Test
    public void shouldBeDifferentAge() {
        assertNotEquals(customer.getAge(birthday), customer1.getAge(birthday));
    }
    @Test
    public void shouldBeTheSameGender() {
        assertEquals(customer.getGender(), customer2.getGender());
    }
    @Test
    public void shouldBeDifferentGender() {
        assertNotEquals(customer.getGender(), customer1.getGender());
    }
    @Test public void shouldAddAccount() {
        customer.addAccount(new Account("123456789"));
        assertEquals(1, customer.getAccountList().size());
    }

    @Test public void shouldRemoveAccount() {
        customer.addAccount(new Account("123456789"));
        customer.removeAccount(new Account("123456789"));
        assertEquals(0, customer.getAccountList().size());
    }

    @Test public void shouldSumBalance() {
        customer.addAccount(new Account("123"));
        customer.addAccount(new Account("345"));
        var account1 = customer.getAccount("123");
        var account2 = customer.getAccount("345");
        account1.deposit(BigDecimal.valueOf(100.00));
        account2.deposit(BigDecimal.valueOf(200.00));
        assertEquals(BigDecimal.valueOf(300.00), customer.getBalance());
    }

    @Test public void shouldFilterFromAllTransactions() {
        customer.addAccount(new Account("123"));
        customer.addAccount(new Account("345"));
        var account1 = customer.getAccount("123");
        var account2 = customer.getAccount("345");
        account1.deposit(BigDecimal.valueOf(100.00));
        account2.deposit(BigDecimal.valueOf(200.00));
        var x = customer.filterTransactions(Transaction.Type.DEPOSIT);
        assertEquals(2, x.size());
    }
}