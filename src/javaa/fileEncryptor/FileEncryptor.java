package javaa.fileEncryptor;

import javaa.exception.FileNotExistsException;
import javaa.exception.InvalidEncryptionKeyException;
import javaa.exception.InvalidFilePathException;
import javaa.helpFunctions.HelpFunctions;
import javaa.key.SimpleIKey;
import javaa.typesOfEncryption.IEncryptionAlgorithm;
import javaa.key.IKey;
import javaa.key.KeyHelper;
import javaa.typesOfEncryption.ShiftMultiplyIEncryption;
import javaa.typesOfEncryption.ShiftUpIEncryption;

import java.io.*;


public class FileEncryptor {
    static final int BUFFER = 500;
    private final IEncryptionAlgorithm encryptionAlgorithm;

    public FileEncryptor(IEncryptionAlgorithm IEncryptionAlgorithm) {
        this.encryptionAlgorithm = IEncryptionAlgorithm;
    }

    public void encryptFile(String originalFilePath) throws InvalidFilePathException, FileNotExistsException {
        if (!HelpFunctions.isValidPath(originalFilePath))
            throw new InvalidFilePathException("The Path is not Valid");
        if (!HelpFunctions.isFileExists(originalFilePath))
            throw new FileNotExistsException("File Not Exists");
        File file = new File(originalFilePath);
        IKey randomNumber = encryptionAlgorithm.generateKey();
        String path = HelpFunctions.getNewName(file);
        File keyFile = KeyHelper.keyFileCreator(path, randomNumber);
        String fileEncryptedPath = path + "_encrypted.txt";
        writeOrReadFromFile(originalFilePath, fileEncryptedPath, randomNumber, true);
        System.out.println("The Encrypted Message Is At : " + fileEncryptedPath);
        System.out.println("The Key Is At : " + keyFile.getPath());
    }


    public void decryptFile(String encryptedFilePath, String keyPath) throws InvalidFilePathException, InvalidEncryptionKeyException, FileNotExistsException {
        if (!HelpFunctions.isValidPath(encryptedFilePath))
            throw new InvalidFilePathException("The Path Of The File is not Valid");
        if (!HelpFunctions.isFileExists(encryptedFilePath))
            throw new FileNotExistsException("The File Not Exists");
        if (!HelpFunctions.isValidPath(keyPath))
            throw new InvalidFilePathException("The Path Of The Key is not Valid");
        if (!HelpFunctions.isFileExists(keyPath))
            throw new FileNotExistsException("The File Of The Key Is Not Exists");
        if (!KeyHelper.checkIfKeyValid(encryptionAlgorithm, keyPath))
            throw new InvalidEncryptionKeyException("The Value Of The Key Is Not Valid");
        File encFile = new File(encryptedFilePath);
        String path = HelpFunctions.getNewName(encFile);
        String decryptedFileName = path + "_decrypted.txt";
        File decryptedFile = new File(decryptedFileName);
        IKey key = KeyHelper.keyFileReaderByType(encryptionAlgorithm, keyPath);
        writeOrReadFromFile(encryptedFilePath, decryptedFileName, key, false);
        System.out.println("The Decrypted Message Is At : " + decryptedFile.getPath());
    }

    public void writeOrReadFromFile(String fileToRead, String fileToWrite, IKey key, boolean isEncryption) {
        try (FileInputStream fileInputStream = new FileInputStream(fileToRead);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(fileToWrite);
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
            System.out.println(e.getMessage());
        }
    }
}
