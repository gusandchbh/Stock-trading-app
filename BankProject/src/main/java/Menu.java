import Utility.UserInput;


public class Menu {
    private final Controller controller = new Controller();
    private final UserInput input = UserInput.getInstance();

    public void startPage() {
        System.out.println("± Welcome to The Bank! ±");
        char option;
        do {
            System.out.println("Choose one of the following options: ");
            System.out.println("1. Register as a customer.");
            System.out.println("2. Login.");
            System.out.println("Enter q to exit the application.");
            option = input.readChar("Please type an option number: ");
            switch (option) {
                case '1':
                    Customer x = controller.createCustomer();
                    System.out.println(x);
                    break;
                case '2':
                    var currentUser = controller.login();
                    if (currentUser != null) {
                        System.out.println("Welcome " + currentUser.getFullName() + "!");
                        System.out.println("± You are now logged in! ±");
                        bankPage(currentUser);
                    } else {
                        System.out.println("Login failed.");
                    }
                    break;
                default:
                    break;
            }
        } while (option != 'q');
        System.exit(1);
    }

    public void bankPage(Customer currentUser) {
        System.out.println();
        char option;
        do {
            System.out.println("Choose one of the following options: ");
            System.out.println("1. Make a deposit.");
            System.out.println("2. Make a withdrawal.");
            System.out.println("3. Make a transfer between own accounts.");
            System.out.println("4. Go to accounts page.");
            System.out.println("Enter q to return back to the start page.");
            option = input.readChar("Please type an option number: ");
            switch (option) {
                case '1':
                    controller.deposit(currentUser);
                    break;
                case '2':
                    controller.withdraw(currentUser);
                    break;
                case '3':
                    controller.transferOwnAccounts(currentUser); // transfer between own accounts
                    break;
                case '4':
                    accountsPage(currentUser);
                    break;
                default:
                    break;
            }
        } while (option != 'q');
    }

    public void accountsPage(Customer currentUser){
        System.out.println("± You are now logged in! ±");
        char option;
        do {
            System.out.println("Choose one of the following options: ");
            System.out.println("1. Create a new account.");
            System.out.println("2. Close an account.");
            System.out.println("3. see your accounts.");
            System.out.println("4. Go to bank page.");
            System.out.println("Enter q to return back to the start page.");
            option = input.readChar("Please type an option number: ");
            switch (option) {
                case '1':

                    break;
                case '2':

                    break;
                case '3':

                    break;
                case '4':
                    bankPage(currentUser);
                    break;
                default:
                    break;
            }
        } while (option != 'q');
    }
}
