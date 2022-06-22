import Utility.LocalDateFormatter;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Objects;



class Customer extends User {

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
    private final String firstname;
    private final String lastname;
    private final LocalDate birthDate;
    private final LocalDate regDate;
    private final Gender gender;

    public Customer(String username, String password, String firstname, String lastname, LocalDate birthDate, Gender gender){
        super(username, password);
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.gender = gender;
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
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return firstname.equals(customer.firstname) && lastname.equals(customer.lastname) && birthDate.equals(customer.birthDate) && regDate.equals(customer.regDate) && gender == customer.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstname, lastname, birthDate, regDate, gender);
    }

    public static void main (String[] args){
        LocalDate birthDate = LocalDate.of(1993, Month.SEPTEMBER, 1);
        Customer customer = new Customer("example", "example", "Johnny", "Depp", birthDate, Customer.Gender.MALE);
        System.out.println(customer.gender);
    }
}
