import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TransactionTest {
    LocalDate today = LocalDate.now();

    Customer customer1 = new Customer("test", "test", "Christopher Andersson", today, Customer.Gender.MALE);
    Customer customer2 = new Customer("test2", "test2", "Miranda Nilhag", today, Customer.Gender.FEMALE);
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
