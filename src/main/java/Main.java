package main.java;

import main.java.exception.InvalidFilePathException;
import main.java.menu.Menu;
import main.java.exception.FileNotExistsException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InvalidFilePathException, FileNotExistsException {
            Menu.StartMenu();
    }
}
