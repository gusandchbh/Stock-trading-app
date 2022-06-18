package View;

import Utility.UserInput;

public class Menu {

    public void startPage() throws Exception {
        String option;
        do {
            option = UserInput.readString("Please type an option number: ");
            switch (option) {
                case "0":
                    break;
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                break;
                default:
                    break;
            }
        } while (true);
    }
}
