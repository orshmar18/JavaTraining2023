package main.java.fileEncryptor;

import main.java.exception.FileNotExistsException;
import main.java.exception.InvalidEncryptionKeyException;
import main.java.exception.InvalidFilePathException;
import main.java.helpFunctions.HelpFunctions;
import main.java.typesOfEncryption.IEncryptionAlgorithm;
import main.java.key.IKey;
import main.java.key.KeyHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


public class FileEncryptor<T extends IKey> {
    static final int BUFFER = 500;
    private final IEncryptionAlgorithm<T> encryptionAlgorithm;
    private static final Logger logger = LogManager.getLogger(FileEncryptor.class);

    public FileEncryptor(IEncryptionAlgorithm<T> IEncryptionAlgorithm) {
        this.encryptionAlgorithm = IEncryptionAlgorithm;
    }

    public void encryptFile(String originalFilePath) throws InvalidFilePathException, FileNotExistsException {
        logger.info("Start To Encrypt File");
        validatePath(originalFilePath);
        T key = encryptionAlgorithm.generateKey();
        String path = HelpFunctions.removeFileExtension(originalFilePath);
        File keyFile = KeyHelper.keyFileCreator(path+"_", key);
        String fileEncryptedPath = path + "_encrypted.txt";
        doEncryptOrDecrypt(originalFilePath, fileEncryptedPath, key, encryptionAlgorithm::dataEncryption);
        System.out.println("The Encrypted Message Is At : " + fileEncryptedPath);
        System.out.println("The Key Is At : " + keyFile.getPath());
        logger.info("Finish To Encrypt File");
    }

    public void decryptFile(String encryptedFilePath, String keyPath) throws InvalidFilePathException, InvalidEncryptionKeyException, FileNotExistsException {
        logger.info("Start To Decrypt File");
        validatePath(encryptedFilePath);
        validateKey(keyPath,encryptionAlgorithm);
        String path = HelpFunctions.removeFileExtension(encryptedFilePath);
        String decryptedFileName = path + "_decrypted.txt";
        File decryptedFile = new File(decryptedFileName);
        T key = KeyHelper.keyFileReaderByType(encryptionAlgorithm, keyPath);
        doEncryptOrDecrypt(encryptedFilePath, decryptedFileName, key, encryptionAlgorithm::dataDecryption);
        System.out.println("The Decrypted Message Is At : " + decryptedFile.getPath());
        logger.info("Finish To Decrypt File");
    }


    public void doEncryptOrDecrypt(String filePathToRead, String filePathToWrite, T key, ByteBufferProcessor<T> processorData) {
        try (FileInputStream fileInputStream = new FileInputStream(filePathToRead);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(filePathToWrite);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            while (dataInputStream.available() > 0) {
                byte[] fileData = new byte[BUFFER];
                int bytesRead = dataInputStream.read(fileData, 0, BUFFER);
                byte[] bytesToWrite = processorData.dataProcessor(fileData,bytesRead,key);
                dataOutputStream.write(bytesToWrite);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void validatePath(String filePath) throws InvalidFilePathException, FileNotExistsException {
        validatePath(filePath, "The Path Of The File is not Valid");
        validateFileExists(filePath, "The File Not Exists");
    }

    public void validateKey(String keyPath, IEncryptionAlgorithm<T> encryptionAlgorithm) throws InvalidFilePathException, FileNotExistsException, InvalidEncryptionKeyException {
        validatePath(keyPath, "The Path Of The Key is not Valid");
        validateFileExists(keyPath, "The File Of The Key Is Not Exists");
        validateKey(encryptionAlgorithm, keyPath, "The Value Of the Key Is Not Valid");
    }
    public void validatePath(String path, String errorMessage) throws InvalidFilePathException {
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
