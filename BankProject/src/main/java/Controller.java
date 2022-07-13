import Utility.UserInput;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class Controller {

    private final List<Customer> customerList = new ArrayList<>();
    private final HashSet<String> accountNumbers = new HashSet<>();
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

    // REGEX for 1993-09-25
    boolean isValidDate(String input) {
        if  (!Pattern.matches("^(19|20)\\d\\d-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", input)) {
            return false;
        }
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

    public boolean hasNoAccount(Customer customer){
        var accountList = customer.getAccountList();
        return accountList.isEmpty();
    }

    public void printCustomerAccounts(Customer customer){
        var accountList = customer.getAccountList();
        if (hasNoAccount(customer)){
            System.out.println("You don't have any accounts. Please open one.");
        } else {
            int counter = 1;
            for (Account a : accountList) {
                System.out.println("Account " + counter + ": " + System.lineSeparator() + "Account number: " + a.getAccountNumber()
                        + System.lineSeparator() + "Balance: " + a.getBalance());
                counter++;
            }
        }
    }

    public void withdraw(Customer customer) {
        if (hasNoAccount(customer)) {
            System.out.println("You don't have any accounts. Please open one.");
        } else {
            printCustomerAccounts(customer);
            int account = input.readInt("Enter the number associated to the account you want to withdraw from: ");
            while (!validChoiceOfAccount(customer, account)) {
                account = input.readInt("Enter the number associated to the account you want to withdraw from: ");
            }
            double amount = input.readDouble("Enter the amount you want to withdraw: ");
            while (amount < 0 || customer.getAccountList().get(account - 1).getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
                amount = input.readDouble("Invalid amount, please enter the amount you want to withdraw: ");
            }
            customer.getAccountList().get(account - 1).withdraw(BigDecimal.valueOf(amount));
            System.out.println("You have withdrawn " + amount + " EUR from account number: " + customer.getAccountList().get(account - 1).getAccountNumber() + ".");
        }
    }
        public void transferOwnAccounts(Customer customer) {
            var accountsList = customer.getAccountList();
            if (accountsList.size() < 2) {
                System.out.println("You don't have two accounts. Go to accounts page to open a new account.");
            } else {
                printCustomerAccounts(customer);
                int fromAccount = input.readInt("Enter the number associated with the account you want to transfer from: ");
                while (!validChoiceOfAccount(customer, fromAccount)) {
                    fromAccount = input.readInt("Enter the number associated with the account you want to transfer from: ");
                }
                var account1 = customer.getAccountList().get(fromAccount - 1);
                int toAccount = input.readInt("Enter the number associated to the account you want to transfer to: ");
                while (!validChoiceOfAccount(customer, toAccount)) {
                    toAccount = input.readInt("Enter the number associated to the account you want to transfer to: ");
                }
                var account2 = customer.getAccountList().get(toAccount - 1);
                double amount = input.readDouble("Enter the amount you want to transfer: ");
                while (amount < 0 || customer.getAccountList().get(fromAccount - 1).getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
                    amount = input.readDouble("Invalid amount, please enter the amount you want to transfer: ");
                }
                if (fromAccount == toAccount) {
                    System.out.println("You can't transfer to the same account you are transferring from.");
                } else {
                    account1.transfer(account2, BigDecimal.valueOf(amount));
                }
                System.out.println("You have transferred " + amount + " from " + account1.getAccountNumber() + " to " + account2.getAccountNumber() + ".");
            }
        }


        public boolean validChoiceOfAccount(Customer customer, int account){
          if (account < 1 || account > customer.getAccountList().size()) {
              System.out.println("Enter the number associated with the account. Choose from 1 to " + customer.getAccountList().size() +".");
              return false;
          } else if (customer.getAccountList().size() == 1 && account != 1) {
              System.out.println("You have one account, please choose 1: ");
              return false;
          }
            return true;
        }

    public void deposit(Customer customer) {
        if (hasNoAccount(customer)) {
            System.out.println("You don't have any accounts. Please open one.");
        } else {
            printCustomerAccounts(customer);
            int account = input.readInt("Enter the number associated to the account you want to deposit to: ");
            while (!validChoiceOfAccount(customer, account)) {
                account = input.readInt("Enter the number associated to the account you want to deposit to: ");
            }
            double amount = input.readDouble("Enter the amount you want to deposit: ");
            while (amount < 0) {
                amount = input.readDouble("Please enter a positive amount: ");
            }
            customer.getAccountList().get(account - 1).deposit(BigDecimal.valueOf(amount));
            System.out.println("You have deposited " + amount + " EUR to account number: " + customer.getAccountList().get(account - 1).getAccountNumber() + ".");
        }
    }
    // Generate unique random account number
    public String generateAccountNumber(){
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++){
            int x = (int) (Math.random() * 10);
            accountNumber.append(x);
        }
        while (accountNumberExists(accountNumber.toString())){
            accountNumber = new StringBuilder();
            for (int i = 0; i < 10; i++){
                int x = (int) (Math.random() * 10);
                accountNumber.append(x);
            }
        }
        return accountNumber.toString();
    }

    public boolean accountNumberExists(String accountNumber){
        for (String s : accountNumbers){
            if (s.contains(accountNumber)){
                return true;
            }
        }
        return false;
    }

    public void createAccount(Customer customer){
        String accountNumber = generateAccountNumber();
        customer.addAccount(new Account(accountNumber));
    }

    public void closeAccount(Customer customer) { // Kan inte avsluta om kund har 1 konto
        if (hasNoAccount(customer)) {
            System.out.println("You have no accounts. Please open an account.");
        } else {
            System.out.println("Please select the account you want to close: ");
            printCustomerAccounts(customer);
            int account = input.readInt("Enter the number associated to the account you want to close: ");
            while (!validChoiceOfAccount(customer, account)) {
                account = input.readInt("Enter the number associated to the account you want to close: ");
            }
            var accountToClose = customer.getAccountList().get(account - 1);
            if (accountToClose.getBalance().compareTo(BigDecimal.valueOf(0)) > 0) {
                System.out.println("You have a balance on this account. Please withdraw all money before closing it.");
            } else {
                customer.getAccountList().remove(account - 1);
                System.out.println("You have closed account number: " + accountToClose.getAccountNumber() + ".");
            }
        }
    }




    public Customer createCustomer() {
        LocalDate birthDate = enterBirthdate();
        String username = chooseUsername();
        String password = choosePassword();
        String fullName = enterFullName();
        Customer.Gender gender = chooseGender();
        Customer customer = new Customer(username, password, fullName, birthDate, gender);
        String accountNumber = generateAccountNumber();
        String accountNumber2 = generateAccountNumber();
        accountNumbers.add(accountNumber);
        accountNumbers.add(accountNumber2);
        customerList.add(customer);
        customer.addAccount(new Account(accountNumber));
        customer.addAccount(new Account(accountNumber2));
        System.out.println("Thank you for registering " + customer.getFullName().split(" ")[1] + "!");
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
    // Print all transactions for a specific account
    public void accountStatement(Customer customer){
        var accountsList = customer.getAccountList();
        System.out.println("Please select the account you want to see the statement for: ");
        printCustomerAccounts(customer);
        int account = input.readInt("Enter the number associated to the account you want to see the statement for: ");
        while (!validChoiceOfAccount(customer, account)) {
            account = input.readInt("Enter the number associated to the account you want to see the statement for: ");
        }
        var account1 = accountsList.get(account - 1);
        System.out.println("Account statement for account number: " + account1.getAccountNumber());
        System.out.printf("%-20s %-20s %-20s %-20s %-20s \n", "Date", "Description", "Amount", "Balance", "Type");
        for (Transaction transaction : account1.getTransactionList()) {
            System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", transaction.getDate(), "lol", transaction.getAmount(), account1.getBalance(), transaction.getType().toString());
        }

    }

    public static void main (String[]args){
        Customer customer = new Customer("kalle", "kalle", "kalle kalle", LocalDate.now(), Customer.Gender.MALE);
        customer.addAccount(new Account("1234"));
        customer.addAccount(new Account("9999"));
        Controller controller = new Controller();
        controller.closeAccount(customer);
        controller.closeAccount(customer);

    }

}
