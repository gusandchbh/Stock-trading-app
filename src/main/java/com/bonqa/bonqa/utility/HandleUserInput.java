package com.bonqa.bonqa.utility;

import com.bonqa.bonqa.model.Customer;

import java.time.LocalDate;

public class HandleUserInput {
    private static final UserInput INPUT = UserInput.getInstance();

    public static LocalDate enterBirthdate() {
        String birthdate = INPUT.readString("Enter your birthdate (yyyy-mm-dd): ");
        while (!PatternMatching.isValidDate(birthdate)) {
            birthdate = INPUT.readString("Enter your birthdate (yyyy-mm-dd): ");
        }
        return LocalDate.parse(birthdate);
    }

    public static String enterFullName() {
        String fullName = INPUT.readString("Enter your full name: ");
        while (!PatternMatching.validFullName(fullName)) {
            fullName = INPUT.readString("Enter your full name: ");
        }
        return fullName;
    }

    public static String chooseUsername() {
        var username = INPUT.readString("Please choose a username: "); // Mer informativ
        while (!PatternMatching.validUserName(username)) {
            username = INPUT.readString("Username is invalid, please choose a different username: ");
        }
        return username;
    }

    public static String choosePassword() {
        var password = INPUT.readString("Please choose a password: "); // Mer informativ
        while (!PatternMatching.validPassword(password)) {
            password = INPUT.readString("Password is invalid, please choose a different password: ");
        }
        return password;
    }

    public static Customer.Gender chooseGender() {
        int x = INPUT.readInt("Enter 1 if you are male and 2 if you are female.");
        while (!validGender(x)) {
            x = INPUT.readInt("Enter 1 if you are male and 2 if you are female.");
        }
        if (x == 1) {
            return Customer.Gender.MALE;
        } else {
            return Customer.Gender.FEMALE;
        }
    }

    private static boolean validGender(int x) {
        return x == 1 || x == 2;
    }

}
