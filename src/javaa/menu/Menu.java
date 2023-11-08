package javaa.menu;

import javaa.exception.FileNotExistsException;
import javaa.exception.InvalidEncryptionKeyException;
import javaa.exception.InvalidFilePathException;
import javaa.fileEncryptor.FileEncryptor;
import javaa.helpFunctions.HelpFunctions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Menu {
    static final char ENCRYPTION = 'e';
    static final char DECRYPTION = 'd';
    private static final Logger logger = LogManager.getLogger(Menu.class);

    public static void StartMenu() {
        logger.info("Menu Starting");
        char choiceEncryption = HelpFunctions.UserInputChoice();
        String filePath;
        char chooseTypeOfEncryption = HelpFunctions.UserInputTypeOfEncryption();
        FileEncryptor fileEncryptor = new FileEncryptor(HelpFunctions.TypeOfEncryption(chooseTypeOfEncryption));
        filePath = HelpFunctions.userInputFilePath();
        switch (choiceEncryption) {
            case ENCRYPTION -> {
                try {
                    fileEncryptor.encryptFile(filePath);
                } catch (InvalidFilePathException | FileNotExistsException e) {
                    logger.error(e.getMessage());
                    System.out.println("Got Exception While Trying To Encrypt File " + filePath + "\nSee Causing Exception: " + e.getMessage());
                }
            }
            case DECRYPTION -> {
                System.out.print("Please Enter The Key Path : ");
                Scanner scan = new Scanner(System.in);
                String keyPath = scan.next();
                scan.close();
                try {
                    logger.info("Go To FileEncryptor To Make Decryption");
                    fileEncryptor.decryptFile(filePath, keyPath);
                } catch (InvalidFilePathException | InvalidEncryptionKeyException | FileNotExistsException e) {
                    logger.error(e.getMessage());
                    System.out.println("Got Exception While Trying To Decrypt File " + filePath + "\nSee Causing Exception: " + e.getMessage());
                }
            }
            default -> {
                System.out.println("You Did Not Enter One Of The Options\nPlease Try Again");
                logger.warn("The User Did Not Pick One Of The Options");
                StartMenu();
            }
        }
        logger.info("Menu Ending");
    }
}