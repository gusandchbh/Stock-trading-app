package com.bonqa.bonqa.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class PatternMatching {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean validPassword(String password) {
        return Pattern.matches("^[a-zA-Z0-9]{5,15}$", password);
    }

    public static boolean validUserName(String username) {
        return Pattern.matches("^[a-zA-Z0-9]{5,15}$", username);
    }

    public static boolean isValidDate(String input) {
        if  (!Pattern.matches("^(19|20)\\d\\d-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", input)) {
            return false;
        }

        try {
            FORMAT.setLenient(false);
            FORMAT.parse(input);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean validFullName(String fullName) {
        return Pattern.matches("^[a-zA-Z]{1,20} [a-zA-Z]{1,20}$", fullName);
    }
}
