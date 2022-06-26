import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    LocalDate today = LocalDate.now();
    LocalDate yesterday = LocalDate.now().minusDays(1);

    Transaction transaction1 = new Transaction(BigDecimal.valueOf(100.00), Transaction.Type.DEPOSIT);


    @Test
    public void shouldBeEqual() {

    }

}
