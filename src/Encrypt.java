import java.io.*;
import java.security.SecureRandom;

public class Encrypt {
    int C = 255;


    public void DoEncrypt(String filepath) throws IOException {

        SecureRandom random = new SecureRandom();
        int rnd = random.nextInt(C);
        File file = new File(filepath);
        String path = getNewPath(file);

        String fileKeyName = path + "_key.txt";
        File keyFile = new File(fileKeyName);
        BufferedWriter keyWriter = new BufferedWriter(new FileWriter(keyFile));
        keyWriter.write(Integer.toString(rnd));
        keyWriter.close();

        String fileEncryptedName = path + "_encrypted.txt";
        File fileEcrypted = new File(fileEncryptedName);

        BufferedReader fileReader = new BufferedReader(new FileReader(filepath));
        String massage = fileReader.readLine();

        char[] charMessage = massage.toCharArray();

        for(int i = 0 ; i <charMessage.length ; i++){
            charMessage[i] = (char) ((charMessage[i] + rnd) % C);
        }
        String encryptedMessage = new String(charMessage);


        BufferedWriter encWriter = new BufferedWriter(new FileWriter(fileEcrypted));
        encWriter.write(encryptedMessage);
        encWriter.close();


        System.out.println(fileEncryptedName);
        System.out.println(keyFile.getPath());



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
