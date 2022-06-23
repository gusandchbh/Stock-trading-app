import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CustomerTest {
    LocalDate birthday = LocalDate.of(1993, Month.SEPTEMBER, 1);
    LocalDate birthday2 = LocalDate.of(1990, Month.SEPTEMBER, 2);
    Customer customer = new Customer("example", "example", "Johnny Depp", birthday, Customer.Gender.MALE);
    Customer customer1 = new Customer("example", "example", "Johnny Depp", birthday, Customer.Gender.MALE);
    Customer customer2 = new Customer("Example", "example", "Johnny Depp", birthday2, Customer.Gender.FEMALE);
    @Test
    public void shouldBeEqual(){
        assertEquals(customer, customer1);
    }

    @Test public void shouldNotBeEqual(){
        assertNotEquals(customer, customer2);
    }
    @Test
    public void shouldBeTheSameAge(){
        assertEquals(customer.getAge(birthday), customer1.getAge(birthday));
    }
    @Test
    public void shouldBeDifferentAge(){
        assertNotEquals(customer.getAge(birthday), customer2.getAge(birthday));
    }
    @Test
    public void shouldBeTheSameGender(){
        assertEquals(customer.getGender(), customer1.getGender());
    }
    @Test
    public void shouldBeDifferentGender(){
        assertNotEquals(customer.getGender(), customer2.getGender());
    }

}
