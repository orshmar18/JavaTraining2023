import java.io.*;


public class FileEncryptor {
    static final int BUFFER = 500;
    private final EncryptionAlgorithm encryptionAlgorithm;

    public FileEncryptor(EncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }



    public void encryptFile(String originalFilePath) {
        Key randomNumber = encryptionAlgorithm.generateKey();

        File file = new File(originalFilePath);
        String path = HelpFunctions.getNewName(file);
        File keyFile = KeyHalper.keyFileCreator(path,randomNumber);

        String fileEncryptedName = path + "_encrypted.txt";
        try(FileInputStream fileInputStream = new FileInputStream(originalFilePath);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(fileEncryptedName);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){
            while (dataInputStream.available() > 0){
                byte[] fileData = new byte[BUFFER];
                int bytesRead = dataInputStream.read(fileData,0,BUFFER);
                byte[] bytesEncrypt = encryptionAlgorithm.dataEncryption(fileData,bytesRead,randomNumber);
                dataOutputStream.write(bytesEncrypt);
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
            Key key;
            if ((encryptionAlgorithm.getClass() == RepeatEncryption.class)){
                if(((RepeatEncryption) encryptionAlgorithm).getEncAlg().getClass() == DoubleEncryption.class) {
                key = KeyHalper.complexKeyFileReader(keyPath);
                }else {
                    key = KeyHalper.simpleKeyFileReader(keyPath);
                }
                }else {
                if (encryptionAlgorithm.getClass() == DoubleEncryption.class) {
                    key = KeyHalper.complexKeyFileReader(keyPath);
                } else {
                    key = KeyHalper.simpleKeyFileReader(keyPath);
                }
            }

            try(FileInputStream fileInputStream = new FileInputStream(encryptedFilePath);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                FileOutputStream fileOutputStream = new FileOutputStream(decryptedFileName);
                DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){
                while (dataInputStream.available() > 0){
                    byte[] fileData = new byte[BUFFER];
                    int bytesRead = dataInputStream.read(fileData,0,BUFFER);
                    byte[] bytesDecrypt = encryptionAlgorithm.dataDecryption(fileData,bytesRead,key);
                    dataOutputStream.write(bytesDecrypt);
                }
            }catch (IOException e){}
            System.out.println("The Decrypted Message Is At : "+decryptedFile.getPath());
        }else
            System.out.println("File Key Not Found");
    }

}
