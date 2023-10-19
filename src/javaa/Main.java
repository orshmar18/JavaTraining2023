package javaa;

import javaa.exception.InvalidPathException;
import javaa.menu.Menu;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InvalidPathException {
        int i = 0;
        while (i < 10) {
            Menu.StartMenu();
        i++;
        }
    }
}
