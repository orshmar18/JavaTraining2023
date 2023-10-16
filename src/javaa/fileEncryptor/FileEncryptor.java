package javaa.fileEncryptor;

import javaa.helpFunctions.HelpFunctions;
import javaa.typesOfEncryption.IEncryptionAlgorithm;
import javaa.key.IKey;
import javaa.key.KeyHelper;

import java.io.*;


public class FileEncryptor {
    static final int BUFFER = 500;
    private final IEncryptionAlgorithm encryptionAlgorithm;

    public FileEncryptor(IEncryptionAlgorithm IEncryptionAlgorithm) {
        this.encryptionAlgorithm = IEncryptionAlgorithm;
    }

    public void encryptFile(String originalFilePath) {
        IKey randomNumber = encryptionAlgorithm.generateKey();
        File file = new File(originalFilePath);
        String path = HelpFunctions.getNewName(file);
        File keyFile = KeyHelper.keyFileCreator(path, randomNumber);
        String fileEncryptedPath = path + "_encrypted.txt";
        writeOrReadFromFile(originalFilePath,fileEncryptedPath,randomNumber,true);
        System.out.println("The Encrypted Message Is At : " + fileEncryptedPath);
        System.out.println("The Key Is At : " + keyFile.getPath());
    }


    public void decryptFile(String encryptedFilePath, String keyPath) {
        File keyFile = new File(keyPath);
        if (keyFile.exists()) {
            File encFile = new File(encryptedFilePath);
            String path = HelpFunctions.getNewName(encFile);
            String decryptedFileName = path + "_decrypted.txt";
            File decryptedFile = new File(decryptedFileName);
            IKey key = KeyHelper.keyFileReaderByType(encryptionAlgorithm, keyPath);
            writeOrReadFromFile(encryptedFilePath,decryptedFileName,key,false);
            System.out.println("The Decrypted Message Is At : " + decryptedFile.getPath());
        } else
            System.out.println("File Of Key Not Found");
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
