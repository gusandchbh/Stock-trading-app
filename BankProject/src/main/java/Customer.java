import Utility.LocalDateFormatter;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Random;


class Customer extends User {
    private final String firstname;
    private final String lastname;
    private final LocalDate birthDate;
    private final LocalDate regDate;

    public Customer(String username, String password, String firstname, String lastname, LocalDate birthDate){
        super(username, password);
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.regDate = LocalDate.now();
    }

    public String getFirstname() {
        return firstname;
    }

        public String getLastname() {
            return lastname;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public LocalDate getRegDate() {
            return regDate;
        }

        public long getAge(LocalDate today){
        return ChronoUnit.YEARS.between(this.getBirthDate(), today);
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "firstname='" + firstname + '\'' +
                    ", lastname='" + lastname + '\'' +
                    ", birthDate=" + LocalDateFormatter.formatDate(birthDate) +
                    ", regDate=" + LocalDateFormatter.formatDate(regDate) +
                    '}';
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstname, customer.firstname) && Objects.equals(lastname, customer.lastname) && Objects.equals(birthDate, customer.birthDate) && Objects.equals(regDate, customer.regDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, birthDate, regDate);
    }

    public static void main (String[] args){
        LocalDate birthday = LocalDate.of(1993, Month.SEPTEMBER, 25);

        Customer customer = new Customer("maz", "kalle123", "Christopher", "Andersson", birthday);
            System.out.println(customer);

        Customer customer1 = new Customer("maz", "kalle123", "Christopher", "Andersson", birthday);

        System.out.println(customer.equals(customer1));
    }
}
