package Utility;

import Model.Customer;

import java.time.LocalDate;

import static Utility.PatternMatching.*;

public class HandleUserInput {
    private static final UserInput input = UserInput.getInstance();

    public static LocalDate enterBirthdate() {
        String birthdate = input.readString("Enter your birthdate (yyyy-mm-dd): ");
        while (!isValidDate(birthdate)) {
            birthdate = input.readString("Enter your birthdate (yyyy-mm-dd): ");
        }
        return LocalDate.parse(birthdate);
    }

    public static String enterFullName() {
        String fullName = input.readString("Enter your full name: ");
        while (!validFullName(fullName)) {
            fullName = input.readString("Enter your full name: ");
        }
        return fullName;
    }

    public static String chooseUsername() {
        var username = input.readString("Please choose a username: "); // Mer informativ
        while (!validUserName(username)) {
            username = input.readString("Username is invalid, please choose a different username: ");
        }
        return username;
    }

    public static String choosePassword() {
        var password = input.readString("Please choose a password: "); // Mer informativ
        while (!validPassword(password)) {
            password = input.readString("Password is invalid, please choose a different password: ");
        }
        return password;
    }

    public static Customer.Gender chooseGender() {
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

    private static boolean validGender(int x) {
        return x == 1 || x == 2;
    }

}
