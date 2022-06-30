import Utility.UserInput;

import java.time.LocalDate;

public class Menu {
    Controller controller = new Controller();

    public void startPage() {
        System.out.println("± Welcome to The Bank! ±");
        char option;
        do {
            System.out.println("Choose one of the following options: ");
            System.out.println("1. Register as a customer.");
            System.out.println("2. Login.");
            System.out.println("Enter q to exit the application.");
            option = UserInput.readChar("Please type an option number: ");
            switch (option) {
                case '1':
                    Customer x = controller.createCustomer("maz", "kalle123", "Christopher Andersson", LocalDate.now(), Customer.Gender.MALE);
                    break;
                case '2':
                    Customer y = controller.findCustomer("maz");
                    if (controller.loginSuccess(y, "maz", "kalle123")) {
                        System.out.println("Login success!");
                    } else {
                        System.out.println("Could not log in.");
                    }
                    break;
                default:
                    break;
            }
        } while (option != 'q');
        System.exit(1);
    }

    public void bankPage(Customer currentUser) {
        System.out.println("± You are now logged in! ±");
        char option;
        do {
            System.out.println("Choose one of the following options: ");
            System.out.println("1. Make a deposit.");
            System.out.println("2. Make a withdrawal.");
            System.out.println("3. Make a transfer.");
            System.out.println("4. Go to accounts page.");
            System.out.println("Enter q to return back to the start page.");
            option = UserInput.readChar("Please type an option number: ");
            switch (option) {
                case '1':

                    break;
                case '2':

                    break;
                default:
                    break;
            }
        } while (option != 'q');
    }
}
