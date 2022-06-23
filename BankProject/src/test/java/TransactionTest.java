import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    Transaction transaction = new Transaction("123", 100, "test");
    Transaction transaction1 = new Transaction("123", 100, "test");

    @Test
    public void shouldBeEqual1() {
        assertEquals(transaction, transaction1);
    }
    @Test
public void shouldBeEqual2() {
        assertEquals(transaction.getTransactionID(), transaction1.getTransactionID());
    }
}
