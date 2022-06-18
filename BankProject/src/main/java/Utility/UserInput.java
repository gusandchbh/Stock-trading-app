package Utility;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {
    public static final String EOL = System.lineSeparator();
    public static Scanner input = new Scanner(System.in);

    public static int readInt(String message) {
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

    public static String readString(String message) {
        System.out.print(message);
        return input.nextLine().trim();
    }

    public static double readDouble(String message) {
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

    public static void closeScanner() {
        input.close();
    }
}
