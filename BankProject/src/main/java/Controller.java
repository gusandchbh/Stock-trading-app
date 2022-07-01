import Utility.UserInput;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Controller {

    List<Customer> customerList = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    Controller(){
    }

    public boolean userNameExists(String username) {
        for (Customer customer : customerList) {
            if (customer.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }




    public String choosePassword(){
        String password = "";
        return password;
    }

    public String enterFullName(){
        String fullName = "";
        return fullName;
    }

    boolean isValidDate(String input) {
        try {
            format.setLenient(false);
            format.parse(input);
            return true;
        }
        catch(ParseException e){
            return false;
        }
    }

    public LocalDate enterBirthdate(){
        // REGEX istället för LocalDate.parse(UserInput.readString("Enter your birthdate (yyyy-mm-dd): "));
        String birthdate = UserInput.readString("Enter your birthdate (yyyy-mm-dd): ");
        while (!isValidDate(birthdate)) {
            birthdate = UserInput.readString("Enter your birthdate (yyyy-mm-dd): ");
        }
        return LocalDate.parse(birthdate);
    }

    public String enterUsername(){
        String username = "";
        return username;
    }

    public Customer createCustomer(String username, String password, String fullName, Customer.Gender gender) {

        var birthDate = enterBirthdate();
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
