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
        IKey randomNumber = encryptionAlgorithm.generateKey(); // is it a random number? rename
        String path = HelpFunctions.getNewName(file); // As I mentioned in the definition, it's not readable, rename the method.
        // Consider more meaningfull name then `path` ^^
        File keyFile = KeyHelper.keyFileCreator(path, randomNumber);
        String fileEncryptedPath = path + "_encrypted.txt";
        writeOrReadFromFile(originalFilePath, fileEncryptedPath, randomNumber, true); // write or read? maybe handle or something... rename.
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

        /*
         * Certainly, you can improve the code by encapsulating these checks in a more organized and reusable manner. You can create helper methods to handle these checks, making the code cleaner and easier to understand. Here's an improved version of the code:

```java
public void validateFileAndKeyPaths(String encryptedFilePath, String keyPath, IEncryptionAlgorithm encryptionAlgorithm) throws InvalidFilePathException, FileNotExistsException, InvalidEncryptionKeyException {
    validateFilePath(encryptedFilePath, "The Path Of The File is not Valid");
    validateFileExists(encryptedFilePath, "The File Not Exists");
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
```

In this improved version:

1. The validation checks are encapsulated within the `validateFileAndKeyPaths` method, making the code more organized and concise.

2. Separate helper methods (`validateFilePath`, `validateFileExists`, `validateKey`) handle specific validation tasks, reducing code duplication.

3. The error messages are passed as parameters to the validation methods, making them more customizable.

This approach enhances code readability, maintainability, and reusability.
         */
        File encFile = new File(encryptedFilePath); // meaningfull naming, don't save time in naming.
        String path = HelpFunctions.getNewName(encFile); // rename.
        String decryptedFileName = path + "_decrypted.txt";
        File decryptedFile = new File(decryptedFileName);
        IKey key = KeyHelper.keyFileReaderByType(encryptionAlgorithm, keyPath);
        writeOrReadFromFile(encryptedFilePath, decryptedFileName, key, false);
        // Isn't encrypt and decrypt the same process except some little changes?
        // could you make generic method that gets boolean and both enc/dec use it with a different value?
        System.out.println("The Decrypted Message Is At : " + decryptedFile.getPath());
    }

    public void writeOrReadFromFile(String fileToRead, String fileToWrite, IKey key, boolean isEncryption) {
        try (FileInputStream fileInputStream = new FileInputStream(fileToRead);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(fileToWrite);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) { // are you sure it's the best wat to read files?
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
            System.out.println(e.getMessage()); // Add meanigfull log.
        }
    }
}
