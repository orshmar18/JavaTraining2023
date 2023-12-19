package processSettings;

import Enums.EEncryptionOrDecryption;
import Enums.EEncryptionTypes;
import Enums.ESolutionTypes;
import directoryProcessor.AsyncDirectoryProcessor;
import directoryProcessor.DirectoryProcessor;
import fileEncryptor.FileEncryptor;
import helpFunctions.EncryptionTypeSorts;
import helpFunctions.FileOrDirectoryCases;
import helpFunctions.HelpFunctions;
import helpFunctions.UserInputs;
import menu.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static menu.Menu.StartMenu;

public class ManualCase {
    private static final Logger logger = LogManager.getLogger(ManualCase.class);

    public static void manualCase() {
        logger.info("Manual Starting");

        EEncryptionOrDecryption encryptionOrDecryption = UserInputs.UserInputChoice();
        EEncryptionTypes choiceEncryption = UserInputs.userInputTypeOfEncryption();
        String filePath = UserInputs.userInputFilePath();
        if (!HelpFunctions.checksIfPathIsFileOrDirectory(filePath)) {
            FileEncryptor<?> fileEncryptor = new FileEncryptor(EncryptionTypeSorts.typeOfEncryption(choiceEncryption));
            switch (encryptionOrDecryption) {
                case ENCRYPTION -> {
                    FileOrDirectoryCases.fileEncryptCase(fileEncryptor, filePath);

                }
                case DECRYPTION -> {
                    System.out.print("Please Enter The Key Path : ");
                    Scanner scan = new Scanner(System.in);
                    String keyPath = scan.next();
                    scan.close();
                    FileOrDirectoryCases.fileDecryptCase(fileEncryptor, filePath, keyPath);
                }
                default -> {
                    System.out.println("You Did Not Enter One Of The Options\nPlease Try Again");
                    logger.warn("The User Did Not Pick One Of The Options");
                    StartMenu();
                }
            }
        } else {
            DirectoryProcessor<?> directoryProcessor = new AsyncDirectoryProcessor<>(EncryptionTypeSorts.typeOfEncryption(choiceEncryption));
            switch (encryptionOrDecryption) {
                case ENCRYPTION -> {
                    FileOrDirectoryCases.directoryEncryptCase(directoryProcessor, filePath);
                }
                case DECRYPTION -> {
                    System.out.print("Please Enter The Key Path : ");
                    Scanner scan = new Scanner(System.in);
                    String keyPath = scan.next();
                    scan.close();
                    FileOrDirectoryCases.directoryDecryptCase(directoryProcessor, filePath, keyPath);
                }
                default -> {
                    System.out.println("You Did Not Enter One Of The Options\nPlease Try Again");
                    logger.warn("The User Did Not Pick One Of The Options");
                    StartMenu();
                }
            }
        }

        logger.info("Menu Ending");
    }
}


