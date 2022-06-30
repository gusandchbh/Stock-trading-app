import Utility.UserInput;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Controller {

    List<Customer> customerList = new ArrayList<>();

    Controller(){
    }


    public String chooseUsername(){
        String username = "";
        return username;
    }

    public String choosePassword(){
        String password = "";
        return password;
    }

    public String enterFullName(){
        String fullName = "";
        return fullName;
    }

    public LocalDate enterBirthdate(){
        int birthDate = 0;
        while (String.valueOf(birthDate).length() != 6) {
            birthDate = UserInput.readInt("Please enter your birthday in 6 digits using the following format: yymmdd");
        }
        String birthDate1 = String.valueOf(birthDate);
        String year = birthDate1.substring(0,2);
        String month = birthDate1.substring(2,4);
        String day = birthDate1.substring(4,6);

        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    public Customer createCustomer(String username, String password, String fullName, LocalDate birthDate, Customer.Gender gender) {

        Customer customer = new Customer(username, password, fullName, birthDate, gender);
        customerList.add(customer);
        return customer;
    }

    public Customer findCustomer(String username) {
            for (Customer customer : customerList) {
                if (customer.getUsername().equals(username)) {
                    return customer;
                    }
                }
        return null;
    }

    public boolean loginSuccess(Customer customer, String username, String password){
        return customer.getUsername().equals(username) && customer.getPassword().equals(password);
    }

    public static void main (String[]args){

        Controller controller = new Controller();
        LocalDate d = controller.enterBirthdate();
        System.out.println(d);
    }

}
