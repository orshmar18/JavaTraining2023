package fileEncryptor;

import exception.FileNotExistsException;
import exception.InvalidEncryptionKeyException;
import exception.InvalidFilePathException;
import helpFunctions.HelpFunctions;
import typesOfEncryption.IEncryptionAlgorithm;
import key.IKey;
import key.KeyHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


public class FileEncryptor<T extends IKey> {
    private static final Logger logger = LogManager.getLogger(FileEncryptor.class);
    static final int LIMIT_TIME_MILLIS = 30000;
    static final int BUFFER = 8000;
    private final IEncryptionAlgorithm<T> encryptionAlgorithm;

    public FileEncryptor(IEncryptionAlgorithm<T> IEncryptionAlgorithm) {
        this.encryptionAlgorithm = IEncryptionAlgorithm;
    }

    public void encryptFile(String originalFilePath) throws InvalidFilePathException, FileNotExistsException {
        logger.info("Start To Encrypt File" + this.encryptionAlgorithm.toString() + "on the path" + originalFilePath);
        validatePath(originalFilePath);
        T key = encryptionAlgorithm.generateKey();
        String path = HelpFunctions.removeFileExtension(originalFilePath);
        File keyFile = KeyHelper.keyFileCreator(path + "_", key);
        String fileEncryptedPath = path + "_encrypted.txt";
        long startTime = System.currentTimeMillis();
        doEncryptOrDecrypt(originalFilePath, fileEncryptedPath, key, encryptionAlgorithm::dataEncryption);
        long time = (System.currentTimeMillis() - startTime);
        if (time > LIMIT_TIME_MILLIS)
            logger.warn("Encryption took too much time!, It took : " + time);
        System.out.println("The Encrypted Message Is At : " + fileEncryptedPath);
        System.out.println("The Key Is At : " + keyFile.getPath());
        logger.info("Finish To Encrypt File");
    }

    public void decryptFile(String encryptedFilePath, String keyPath) throws InvalidFilePathException, InvalidEncryptionKeyException, FileNotExistsException {
        logger.info("Start To Decrypt File" + this.encryptionAlgorithm.toString() + "on the path" + encryptedFilePath);
        validatePath(encryptedFilePath);
        validateKey(keyPath, encryptionAlgorithm);
        String path = HelpFunctions.removeFileExtension(encryptedFilePath);
        String decryptedFileName = path + "_decrypted.txt";
        File decryptedFile = new File(decryptedFileName);
        T key = KeyHelper.keyFileReaderByType(encryptionAlgorithm, keyPath);
        long startTime = System.currentTimeMillis();
        doEncryptOrDecrypt(encryptedFilePath, decryptedFileName, key, encryptionAlgorithm::dataDecryption);
        long time = (System.currentTimeMillis() - startTime);
        if (time > LIMIT_TIME_MILLIS)
            logger.warn("Decryption took too much time!, It took : " + time);
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
                byte[] bytesToWrite = processorData.dataProcessor(fileData, bytesRead, key);
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
