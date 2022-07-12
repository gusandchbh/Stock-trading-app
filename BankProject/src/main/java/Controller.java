import Utility.UserInput;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Controller {

    private final List<Customer> customerList = new ArrayList<>();
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private final UserInput input = UserInput.getInstance();

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

    public boolean validUserName(String username) {
        return Pattern.matches("^[a-zA-Z0-9]{5,15}$", username);
    }

    public String chooseUsername(){
        var username = input.readString("Please choose a username: "); // Mer informativ
        while (userNameExists(username)){
            username = input.readString("Username already exists, please choose a different username: ");
        }
        while (!validUserName(username)) {
            username = input.readString("Username is invalid, please choose a different username: ");
        }
        return username;
    }

    public boolean validPassword(String password) {
        return Pattern.matches("^[a-zA-Z0-9]{5,15}$", password);
    }




    public String choosePassword(){
        var password = input.readString("Please choose a password: "); // Mer informativ
        while (!validPassword(password)) {
            password = input.readString("Password is invalid, please choose a different password: ");
        }
        return password;
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
        String birthdate = input.readString("Enter your birthdate (yyyy-mm-dd): ");
        while (!isValidDate(birthdate)) {
            birthdate = input.readString("Enter your birthdate (yyyy-mm-dd): ");
        }
        return LocalDate.parse(birthdate);
    }


    public boolean validFullName(String fullName){
        return Pattern.matches("^[a-zA-Z]{1,20} [a-zA-Z]{1,20}$", fullName);
    }

    public String enterFullName(){
        String fullName = input.readString("Enter your full name: ");
        while (!validFullName(fullName)) {
            fullName = input.readString("Enter your full name: ");
        }
        return fullName;
    }

    public Customer login(){
        String username = input.readString("Please enter your username: ");
        while (!userNameExists(username) || !validUserName(username) || findCustomer(username) == null) {
            username = input.readString("Invalid username, please enter your username: ");
        }
        String password = input.readString("Please enter your password: ");
        for (Customer customer : customerList) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

    public boolean validGender(int x){
        return x == 1 || x == 2;
    }

    public Customer.Gender chooseGender() {
        int x = input.readInt("Enter 1 if you are male and 2 if you are female.");
        while (!validGender(x)){
            x = input.readInt("Enter 1 if you are male and 2 if you are female.");
        }
        if (x == 1){
            return Customer.Gender.MALE;
        } else {
            return Customer.Gender.FEMALE;
        }
    }

    public void printCustomerAccounts(Customer customer){
        var accountList = customer.getAccountList();
        int counter = 1;
        for (Account a : accountList){
            System.out.println("Account " + counter + ": " + System.lineSeparator() + "Account number: " + a.getAccountNumber()
                    + System.lineSeparator() + "Balance: " + a.getBalance());
        }
    }


    public void deposit(Customer customer){

        printCustomerAccounts(customer);
        int account = input.readInt("Enter the number associated to the account you want to deposit to: ");
        while (account < 1 || account > customer.getAccountList().size() + 1){
            if (customer.getAccountList().size() == 0){
                account = input.readInt("You have no accounts, please create one first. ");
            } else if (customer.getAccountList().size() == 1){
                account = input.readInt("You have one account, please enter 1: ");
            } else {
                account = input.readInt("Invalid account, please enter the number associated to the account you want to deposit to: ");
            }
        }
        double amount = input.readDouble("Enter the amount you want to deposit: ");
        while (amount < 0){
            amount = input.readDouble("Please enter a positive amount: ");
        }
        customer.getAccountList().get(account -1).deposit(BigDecimal.valueOf(amount));
    }

    public Customer createCustomer() {
        LocalDate birthDate = enterBirthdate();
        String username = chooseUsername();
        String password = choosePassword();
        String fullName = enterFullName();
        Customer.Gender gender = chooseGender();
        Customer customer = new Customer(username, password, fullName, birthDate, gender);
        customerList.add(customer);
        customer.addAccount(new Account("1234"));
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
