package javaa.menu;

import javaa.exception.FileNotExistsException;
import javaa.exception.InvalidEncryptionKeyException;
import javaa.exception.InvalidFilePathException;
import javaa.fileEncryptor.FileEncryptor;
import javaa.helpFunctions.HelpFunctions;

import java.util.Scanner;

public class Menu {
    static final char ENCRYPTION = 'a';

    static final char DECRYTION = 'b';

    public static void StartMenu() throws InvalidFilePathException, FileNotExistsException {
        char choiceEncryption = HelpFunctions.UserInputChoise();
        Scanner scan = new Scanner(System.in);
        String filePath;
        char chooseTypeOfEncryption = HelpFunctions.UserInputTypeOfEncryption();
        FileEncryptor fileEncryptor = new FileEncryptor(HelpFunctions.TypeOfEncryption(chooseTypeOfEncryption));
        filePath = HelpFunctions.userInputFilePath();
        switch (choiceEncryption) {
            case ENCRYPTION -> {
                try {
                    fileEncryptor.encryptFile(filePath);
                } catch (InvalidFilePathException | FileNotExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
            case DECRYTION -> {
                System.out.print("Please Enter The Key Path : ");
                String keyPath = scan.next();
                try {
                    fileEncryptor.decryptFile(filePath, keyPath);
                } catch (InvalidFilePathException | InvalidEncryptionKeyException | FileNotExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
            default -> System.out.println("You Did Not Enter One Of The Options");
        }
    }
}