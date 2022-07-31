package com.bonqa.bonqa.utility;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class UserInput {
    public static final String INPUT_ERROR_MESSAGE = "Please try again.";
    public static final String CHOOSE_OPTION = "Please choose one of the following options: ";
    public static final String EOL = System.lineSeparator();
    private final Scanner input;
    private static UserInput INSTANCE = null;

    private UserInput() {
       input = new Scanner(System.in);
    }

    public static UserInput getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserInput();
        }
        return INSTANCE;
    }

    public int readInt(String message) {
        try {
            System.out.print(message);
            int value = input.nextInt();
            input.nextLine();
            return value;
        } catch (InputMismatchException e) {
            System.out.println("Invalid entry. Please enter an integer.");
            input.nextLine();
        }
        return readInt(message);
    }

    public String readString(String message) {
        System.out.print(message);
        return input.nextLine().trim();
    }

    public char readChar(String message) {
        System.out.println(message);
        char value = input.next().charAt(0);
        input.nextLine();
        return value;
    }

    public double readDouble(String message) {
        try {
            System.out.print(message);
            double value = input.nextDouble();
            input.nextLine();
            return value;
        } catch (InputMismatchException e) {
            System.out.println("Invalid entry. Please enter a floating point number.");
            input.nextLine();
        }
        return readDouble(message);
    }

    public void closeScanner() {
        input.close();
    }
}
