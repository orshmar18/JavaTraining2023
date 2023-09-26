import java.io.*;
import java.security.SecureRandom;

public class FileEncryptor {

    private final EncryptionAlgorithm encryptionAlgorithm;

    public FileEncryptor(EncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    static final int BUFFER = 500;

    public void encryptFile(String originalFilePath) {
        SecureRandom random = new SecureRandom();
        int randomNumber = encryptionAlgorithm.generateKey(random);

        File file = new File(originalFilePath);
        String path = HelpFunctions.getNewName(file);
        String fileKeyName = path + "_key.txt";
        File keyFile = KeyHalper.keyFileCreator(fileKeyName,randomNumber);

        String fileEncryptedName = path + "_encrypted.txt";
        try(FileInputStream fileInputStream = new FileInputStream(originalFilePath);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(fileEncryptedName);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){
            while (dataInputStream.available() > 0){
                byte[] fileData = new byte[BUFFER];
                int bytesRead = dataInputStream.read(fileData,0,BUFFER);
                String afterEncrypt = encryptionAlgorithm.dataEncryption(fileData,bytesRead,randomNumber);
                dataOutputStream.writeChars(afterEncrypt);
            }
        }catch (IOException e){}
        System.out.println("The Encrypted Message Is At : " + fileEncryptedName);
        System.out.println("The Key Is At : " + keyFile.getPath());
    }


    public void deccryptFile(String encryptedFilePath,String keyPath){
        File keyFile = new File(keyPath);
        if(keyFile.exists()){
            File encFile = new File(encryptedFilePath);
            String path = HelpFunctions.getNewName(encFile);
            String decryptedFileName = path + "_decrypted.txt";
            File decryptedFile = new File(decryptedFileName);
            int key = KeyHalper.keyFileReader(keyPath);
            try(FileInputStream fileInputStream = new FileInputStream(encryptedFilePath);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                FileOutputStream fileOutputStream = new FileOutputStream(decryptedFileName);
                DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){
                while (dataInputStream.available() > 0){
                    byte[] fileData = new byte[BUFFER];
                    int bytesRead = dataInputStream.read(fileData,0,BUFFER);
                    String afterDecrypt = encryptionAlgorithm.dataDecryption(fileData,bytesRead,key);
                    dataOutputStream.writeChars(afterDecrypt);
                }
            }catch (IOException e){}
            System.out.println("The Decrypted Message Is At : "+decryptedFile.getPath());
        }else
            System.out.println("File Key Not Found");
    }

}
