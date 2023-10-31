package javaa;

import javaa.exception.FileNotExistsException;
import javaa.exception.InvalidFilePathException;
import javaa.menu.Menu;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InvalidFilePathException, FileNotExistsException {
        int i = 0;
        // Why do you limit it by 10? if so, why not using for loop?
        while (i < 10) {
            Menu.StartMenu();
        i++;
        }
    }
}
