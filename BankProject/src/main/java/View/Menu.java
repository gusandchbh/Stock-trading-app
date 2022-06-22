package View;

import Utility.UserInput;

public class Menu {

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
                    break;
                case '2':
                    break;
                default:
                    break;
            }
        } while (option != 'q');
        System.exit(1);
    }


    public void registerPage(){
        char option;
        do {
            option = UserInput.readChar("Please choose one of the following options: ");
            switch (option) {
                case '0':
                    break;
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
