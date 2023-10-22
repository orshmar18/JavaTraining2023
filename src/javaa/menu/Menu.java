package javaa.menu;

import javaa.exception.InvalidEncryptionKeyException;
import javaa.exception.InvalidPathException;
import javaa.fileEncryptor.FileEncryptor;
import javaa.helpFunctions.HelpFunctions;

import java.io.File;
import java.util.Scanner;

public class Menu {
    static final char ENCRYPTION = 'a';

    static final char DECRYTION = 'b';

    public static void StartMenu() throws InvalidPathException {
        char choiceEncryption = HelpFunctions.UserInputChoise();
        Scanner scan = new Scanner(System.in);
        String filePath;
        System.out.println("Enter The Path Of The File : ");
        filePath = scan.next();
        char chooseTypeOfEncryption = HelpFunctions.UserInputTypeOfEncryption();
        FileEncryptor fileEncryptor = new FileEncryptor(HelpFunctions.TypeOfEncryption(chooseTypeOfEncryption));
        switch (choiceEncryption) {
            case ENCRYPTION -> {
                try {
                    fileEncryptor.encryptFile(filePath);
                } catch (InvalidPathException e) {
                    System.out.println(e.getMessage());
                }

            }
            case DECRYTION -> {
                System.out.print("Please Enter The Key Path : ");
                String keyPath = scan.next();
                try {
                    fileEncryptor.decryptFile(filePath, keyPath);
                } catch (InvalidPathException | InvalidEncryptionKeyException e){
                    System.out.println(e.getMessage());
                }
            }
            default -> System.out.println("You Did Not Enter One Of The Options");
        }
    }
}