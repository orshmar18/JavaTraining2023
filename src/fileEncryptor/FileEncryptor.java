package fileEncryptor;

import helpFunctions.HelpFunctions;
import typesOfEncryption.DoubleIEncryption;
import typesOfEncryption.IEncryptionAlgorithm;
import typesOfEncryption.RepeatIEncryption;
import key.IKey;
import key.KeyHelper;

import java.io.*;


public class FileEncryptor {
    static final int BUFFER = 500;
    private final IEncryptionAlgorithm IEncryptionAlgorithm;

    public FileEncryptor(IEncryptionAlgorithm IEncryptionAlgorithm) {
        this.IEncryptionAlgorithm = IEncryptionAlgorithm;
    }

    public void encryptFile(String originalFilePath) {
        IKey randomNumber = IEncryptionAlgorithm.generateKey();
        File file = new File(originalFilePath);
        String path = HelpFunctions.getNewName(file);
        File keyFile = KeyHelper.keyFileCreator(path, randomNumber);
        String fileEncryptedName = path + "_encrypted.txt";
        try (FileInputStream fileInputStream = new FileInputStream(originalFilePath);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(fileEncryptedName);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            while (dataInputStream.available() > 0) {
                byte[] fileData = new byte[BUFFER];
                int bytesRead = dataInputStream.read(fileData, 0, BUFFER);
                byte[] bytesEncrypt = IEncryptionAlgorithm.dataEncryption(fileData, bytesRead, randomNumber);
                dataOutputStream.write(bytesEncrypt);
            }
        } catch (IOException e) {
        }
        System.out.println("The Encrypted Message Is At : " + fileEncryptedName);
        System.out.println("The Key Is At : " + keyFile.getPath());
    }


    public void deccryptFile(String encryptedFilePath, String keyPath) {
        File keyFile = new File(keyPath);
        if (keyFile.exists()) {
            File encFile = new File(encryptedFilePath);
            String path = HelpFunctions.getNewName(encFile);
            String decryptedFileName = path + "_decrypted.txt";
            File decryptedFile = new File(decryptedFileName);
            IKey IKey;
            if ((IEncryptionAlgorithm.getClass() == RepeatIEncryption.class)) {
                if (((RepeatIEncryption) IEncryptionAlgorithm).getEncAlg().getClass() == DoubleIEncryption.class) {
                    IKey = KeyHelper.complexKeyFileReader(keyPath);
                } else {
                    IKey = KeyHelper.simpleKeyFileReader(keyPath);
                }
            } else {
                if (IEncryptionAlgorithm.getClass() == DoubleIEncryption.class) {
                    IKey = KeyHelper.complexKeyFileReader(keyPath);
                } else {
                    IKey = KeyHelper.simpleKeyFileReader(keyPath);
                }
            }
            try (FileInputStream fileInputStream = new FileInputStream(encryptedFilePath);
                 DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                 FileOutputStream fileOutputStream = new FileOutputStream(decryptedFileName);
                 DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
                while (dataInputStream.available() > 0) {
                    byte[] fileData = new byte[BUFFER];
                    int bytesRead = dataInputStream.read(fileData, 0, BUFFER);
                    byte[] bytesDecrypt = IEncryptionAlgorithm.dataDecryption(fileData, bytesRead, IKey);
                    dataOutputStream.write(bytesDecrypt);
                }
            } catch (IOException e) {
            }
            System.out.println("The Decrypted Message Is At : " + decryptedFile.getPath());
        } else
            System.out.println("File key.Key Not Found");
    }
}
