package javaa.fileEncryptor;

import javaa.exception.FileNotExistsException;
import javaa.exception.InvalidEncryptionKeyException;
import javaa.exception.InvalidFilePathException;
import javaa.helpFunctions.HelpFunctions;
import javaa.typesOfEncryption.IEncryptionAlgorithm;
import javaa.key.IKey;
import javaa.key.KeyHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


public class FileEncryptor<T> {
    static final int BUFFER = 500;
    private final IEncryptionAlgorithm<T> encryptionAlgorithm;
    private static final Logger logger = LogManager.getLogger(FileEncryptor.class);

    public FileEncryptor(IEncryptionAlgorithm<T> IEncryptionAlgorithm) {
        this.encryptionAlgorithm = IEncryptionAlgorithm;
    }

    public void encryptFile(String originalFilePath) throws InvalidFilePathException, FileNotExistsException {
        logger.info("Start To Encrypt File");
        validateFilePath(originalFilePath);
        T key = encryptionAlgorithm.generateKey();
        String path = HelpFunctions.removeFileExtension(originalFilePath);
        File keyFile = KeyHelper.keyFileCreator(path, key);
        String fileEncryptedPath = path + "_encrypted.txt";
        doEncryptOrDecrypt(originalFilePath, fileEncryptedPath, key, true);
        System.out.println("The Encrypted Message Is At : " + fileEncryptedPath);
        System.out.println("The Key Is At : " + keyFile.getPath());
        logger.info("Finish To Encrypt File");
    }

    public void decryptFile(String encryptedFilePath, String keyPath) throws InvalidFilePathException, InvalidEncryptionKeyException, FileNotExistsException {
        logger.info("Start To Decrypt File");
        validateFilePath(encryptedFilePath);
        validateKeyPaths(keyPath,encryptionAlgorithm);
        String path = HelpFunctions.removeFileExtension(encryptedFilePath);
        String decryptedFileName = path + "_decrypted.txt";
        File decryptedFile = new File(decryptedFileName);
        T key = KeyHelper.keyFileReaderByType(encryptionAlgorithm, keyPath);
        doEncryptOrDecrypt(encryptedFilePath, decryptedFileName, key, false);
        System.out.println("The Decrypted Message Is At : " + decryptedFile.getPath());
        logger.info("Finish To Decrypt File");
    }


    public void doEncryptOrDecrypt(String filePathToRead, String filePathToWrite, T key, boolean isEncryption) {
        try (FileInputStream fileInputStream = new FileInputStream(filePathToRead);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(filePathToWrite);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            while (dataInputStream.available() > 0) {
                byte[] fileData = new byte[BUFFER];
                int bytesRead = dataInputStream.read(fileData, 0, BUFFER);
                byte[] bytesToWrite;
                if (isEncryption) {
                    bytesToWrite = encryptionAlgorithm.dataEncryption(fileData, bytesRead, key);
                } else {
                    bytesToWrite = encryptionAlgorithm.dataDecryption(fileData, bytesRead, key);
                }
                dataOutputStream.write(bytesToWrite);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void validateFilePath(String filePath) throws InvalidFilePathException, FileNotExistsException {
        validateFilePath(filePath, "The Path Of The File is not Valid");
        validateFileExists(filePath, "The File Not Exists");
    }

    public void validateKeyPaths(String keyPath, IEncryptionAlgorithm<T> encryptionAlgorithm) throws InvalidFilePathException, FileNotExistsException, InvalidEncryptionKeyException {
        validateFilePath(keyPath, "The Path Of The Key is not Valid");
        validateFileExists(keyPath, "The File Of The Key Is Not Exists");
        validateKey(encryptionAlgorithm, keyPath, "The Value Of the Key Is Not Valid");
    }
    private void validateFilePath(String path, String errorMessage) throws InvalidFilePathException {
        if (!HelpFunctions.isValidPath(path)) {
            throw new InvalidFilePathException(errorMessage);
        }
    }
    private void validateFileExists(String path, String errorMessage) throws FileNotExistsException {
        if (!HelpFunctions.isFileExists(path)) {
            throw new FileNotExistsException(errorMessage);
        }
    }
    private void validateKey(IEncryptionAlgorithm encryptionAlgorithm, String keyPath, String errorMessage) throws InvalidEncryptionKeyException {
        if (!KeyHelper.checkIfKeyValid(encryptionAlgorithm, keyPath)) {
            throw new InvalidEncryptionKeyException(errorMessage);
        }
    }
}
