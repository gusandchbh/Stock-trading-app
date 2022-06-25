import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AccountTest {

    Account account1 = new Account("12345");
    Account account2 = new Account("54321");

    @Test public void shouldBeEqual() {
        assertEquals(account1, account1);
    }

    @Test public void shouldNotBeEqual() {
        assertNotEquals(account1, account2);
    }

    @Test
    public void shouldDeposit() {
        account1.deposit(BigDecimal.valueOf(100.00));
        assertEquals(account1.getBalance(), BigDecimal.valueOf(100.00));
    }

    @Test public void shouldWithdraw() {
        account1.deposit(BigDecimal.valueOf(100.00));
        account1.withdraw(BigDecimal.valueOf(50.00));
        assertEquals(account1.getBalance(), BigDecimal.valueOf(50.00));
    }

    @Test public void shouldTransfer() {
        account1.deposit(BigDecimal.valueOf(100.00));
        account1.transfer(account2, BigDecimal.valueOf(50.00));
        assertEquals(BigDecimal.valueOf(51.00), account1.getBalance());
        assertEquals(BigDecimal.valueOf(50.00), account2.getBalance());
    }

    @Test public void shouldNotTransfer(){
        account1.deposit(BigDecimal.valueOf(100.00));
        account2.deposit(BigDecimal.valueOf(100.00));
        account1.transfer(account2, BigDecimal.valueOf(100.00));
        assertEquals(account1.getBalance(), BigDecimal.valueOf(100.00));
    }

    @Test public void shouldNotAllowNegativeBalance() {
        account1.deposit(BigDecimal.valueOf(100.00));
        account1.withdraw(BigDecimal.valueOf(200.00));
        assertEquals(account1.getBalance(), BigDecimal.valueOf(100.00));
    }
    @Test public void shouldNotAllowNegativeDeposit() {
        account1.deposit(BigDecimal.valueOf(-100.00));
        assertEquals(account1.getBalance(), BigDecimal.valueOf(0.00));
    }
}
