import java.awt.event.KeyEvent;
import java.io.*;

public class Decrtpt {

    int CONST = 255;

    public void DoDecrypt(String encrypedPath,String keyPath) throws IOException {

        File encFile = new File(encrypedPath);
        File keyFile = new File(keyPath);

        String path = getNewPath(encFile);

        String decryptedFileName = path + "_decrypted.txt";
        File decryptedFile = new File(decryptedFileName);

        BufferedReader keyReader = new BufferedReader(new FileReader(keyFile));
        String line = keyReader.readLine();
        int key = Integer.parseInt(line);
        keyReader.close();


        BufferedReader encryptedFileReader = new BufferedReader(new FileReader(encFile));
        String encryptedMessage = encryptedFileReader.readLine();
        encryptedFileReader.close();


        char[] charEncryptedMessage = encryptedMessage.toCharArray();
        for(int i = 0 ; i < encryptedMessage.length() ; i++){
            charEncryptedMessage[i] =  (char) ((charEncryptedMessage[i] - key) % CONST);
        }
        char[] charDecryptedMessage = new char[charEncryptedMessage.length/2];
        int j = 0,k = 2;
        while (j <(charEncryptedMessage.length / 2)) {
            charDecryptedMessage[j] = charEncryptedMessage[k];
            j++;
            k += 2;
        }

        String decryptedMessage = new String(charDecryptedMessage);

        BufferedWriter decryptedMessageWiter = new BufferedWriter(new FileWriter(decryptedFile));
        decryptedMessageWiter.write(decryptedMessage);
        decryptedMessageWiter.close();


        System.out.println(decryptedFile.getPath());












    }


    private String getNewPath(File file) {
        String finalName = file.getParent()+"\\";
        String nameFileTxt = file.getName();
        int lengh = file.getName().length();
        for (int i = 0 ; i < (lengh - 4) ; i++){
            finalName = finalName + nameFileTxt.charAt(i);
        }
        return finalName;
    }



}
