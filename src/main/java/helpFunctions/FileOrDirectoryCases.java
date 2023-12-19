package helpFunctions;

import directoryProcessor.DirectoryProcessor;
import exception.FileNotExistsException;
import exception.InvalidEncryptionKeyException;
import exception.InvalidFilePathException;
import fileEncryptor.FileEncryptor;
import menu.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class FileOrDirectoryCases {
    private static final Logger logger = LogManager.getLogger(FileOrDirectoryCases.class);

    public static void fileEncryptCase(FileEncryptor<?> fileEncryptor, String filePath){
        try {
            fileEncryptor.encryptFile(filePath);
        } catch (InvalidFilePathException | FileNotExistsException e) {
            logger.error(e.getMessage());
            System.out.println("Got Exception While Trying To Encrypt File " + filePath + "\nSee Causing Exception: " + e.getMessage());
        }
    }

    public static void fileDecryptCase(FileEncryptor<?> fileEncryptor, String filePath,String keyPath){
        try {
            logger.info("Go To FileEncryptor To Make Decryption");
            fileEncryptor.decryptFile(filePath, keyPath);
        } catch (InvalidFilePathException | InvalidEncryptionKeyException | FileNotExistsException e) {
            logger.error(e.getMessage());
            System.out.println("Got Exception While Trying To Decrypt File " + filePath + "\nSee Causing Exception: " + e.getMessage());
        }
    }
    public static void directoryEncryptCase(DirectoryProcessor<?> directoryProcessor, String filePath){
        try {
            directoryProcessor.encryptDirectory(filePath);
        } catch (InvalidFilePathException | FileNotExistsException | IOException e) {
            logger.error(e.getMessage());
            System.out.println("Got Exception While Trying To Encrypt File " + filePath + "\nSee Causing Exception: " + e.getMessage());
        }
    }
    public static void directoryDecryptCase(DirectoryProcessor<?> directoryProcessor, String filePath,String keyPath){
        try {
            directoryProcessor.decryptDirectory(filePath, keyPath);
        } catch (InvalidFilePathException | InvalidEncryptionKeyException | FileNotExistsException | IOException e) {
            logger.error(e.getMessage());
            System.out.println("Got Exception While Trying To Decrypt File " + filePath + "\nSee Causing Exception: " + e.getMessage());
        }
    }

}
