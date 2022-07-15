package Utility;

public class Printing {

    public static void transactionsPagePrint(){
        System.out.println("Choose one of the following options: ");
        System.out.println("1. View account statement");
        System.out.println("2. Search for transactions");
        System.out.println("3. Summarize transactions");
        System.out.println("4. Go to bank page.");
        System.out.println("Enter q to return back to the start page.");
    }

    public static void accountsPagePrint(){
        System.out.println("Choose one of the following options: ");
        System.out.println("1. Create a new account.");
        System.out.println("2. Close an account.");
        System.out.println("3. See your accounts.");
        System.out.println("4. Go to transactions page.");
        System.out.println("Enter q to return back to the start page.");
    }

    public static void bankPagePrint(){
        System.out.println("Choose one of the following options: ");
        System.out.println("1. Make a deposit.");
        System.out.println("2. Make a withdrawal.");
        System.out.println("3. Make a transfer between own accounts.");
        System.out.println("4. Go to the start page.");
        System.out.println("Enter q to return back to the start page.");
    }

    public static void menuPagePrint(){
        System.out.println("Choose one of the following options: ");
        System.out.println("1. Go to bank page.");
        System.out.println("2. Go to account page.");
        System.out.println("3. Go to transaction page.");
        System.out.println("Enter q to log out.");
    }

    public static void loginPagePrint(){
        System.out.println("Choose one of the following options: ");
        System.out.println("1. Register as a customer.");
        System.out.println("2. Login.");
        System.out.println("Enter q to exit the application.");
    }
}


