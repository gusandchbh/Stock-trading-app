package com.bonqa.bonqa.view;

import com.bonqa.bonqa.controller.Controller;
import com.bonqa.bonqa.utility.Printing;
import com.bonqa.bonqa.utility.UserInput;
import com.bonqa.bonqa.model.Customer;

public class Menu {
    private final Controller controller = new Controller();
    private final UserInput input = UserInput.getInstance();

    public void loginPage() {
        System.out.println("± Welcome to The Bank! ±");
        char option;
        do {
        Printing.loginPagePrint();
            option = input.readChar("Please type an option number: ");
            switch (option) {
                case '1':
//                    controller.createCustomer();
                    break;
                case '2':
                    var currentUser = controller.login();
                    if (currentUser != null) {
                        System.out.println("± You are now logged in! ±");
                        menuPage(currentUser);
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

    public void menuPage(Customer currentUser) {
        System.out.println("± Welcome " + currentUser.getFullName() + "! ±");
        char option;
        do {
        Printing.menuPagePrint();
            option = input.readChar("Please type an option number: ");
            switch (option) {
                case '1' -> bankPage(currentUser);
                case '2' -> accountsPage(currentUser);
                case '3' -> transactionsPage(currentUser);
                default -> loginPage();
            }
        } while (option != 'q');
    }

    public void bankPage(Customer currentUser) {
        System.out.println();
        char option;
        do {
        Printing.bankPagePrint();
            option = input.readChar("Please type an option number: ");
            switch (option) {
                case '1' -> controller.deposit(currentUser);
                case '2' -> controller.withdraw(currentUser);
                case '3' -> controller.transferOwnAccounts(currentUser); // transfer between own accounts
                default -> menuPage(currentUser);
            }
        } while (option != 'q');
    }

    public void accountsPage(Customer currentUser) {
        System.out.println("± You are now logged in! ±");
        char option;
        do {
            Printing.accountsPagePrint();
            option = input.readChar("Please type an option number: ");
            switch (option) {
                case '1' -> controller.createAccount(currentUser);
                case '2' -> controller.closeAccount(currentUser);
                case '3' -> controller.printCustomerAccounts(currentUser);
                case '4' -> transactionsPage(currentUser);
                default -> menuPage(currentUser);
            }
        } while (option != 'q');
    }

    public void transactionsPage(Customer currentUser) {
        System.out.println("± You are now logged in! ±");
        char option;
        do {
            Printing.transactionsPagePrint();
            option = input.readChar("Please type an option number: ");
            switch (option) {
//                case '1' -> controller.accountStatement(currentUser);
                //case '2' -> controller.searchTransactions(currentUser);
                //case '3' -> controller.summarizeTransactions(currentUser);
                default -> menuPage(currentUser);
            }
        } while (option != 'q');
    }
}
