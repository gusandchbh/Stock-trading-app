import org.junit.Test;

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
        account1.deposit(100);
        assertEquals(account1.getBalance(), 100, 0);
    }

    @Test public void shouldWithdraw() {
        account1.deposit(100);
        account1.withdraw(50);
        assertEquals(account1.getBalance(), 50, 0);
    }

    @Test public void shouldTransfer() {
        account1.deposit(100);
        account2.deposit(50);
        account1.transfer(account2, 50);
        assertEquals(account1.getBalance(), 50, 0);
        assertEquals(account2.getBalance(), 100, 0);
    }

    @Test public void shouldNotTransfer(){
        account1.deposit(100);
        account2.deposit(50);
        account1.transfer(account2, 101);
        assertEquals(account1.getBalance(), 100, 0);
    }

    @Test public void shouldNotAllowNegativeBalance() {
        account1.deposit(100);
        account1.withdraw(101);
        assertEquals(account1.getBalance(), 100, 0);
    }
    @Test public void shouldNotAllowNegativeDeposit() {
        account1.deposit(-100);
        assertEquals(account1.getBalance(), 0, 0);
    }
}
