import javax.xml.stream.events.DTD;
import java.io.*;
import java.security.SecureRandom;

public class KeyHalper {

    static final int LIMIT = 255;

    public static int keyFileReader(String keyPath){
        int key = 0;
        try(BufferedReader keyReader = new BufferedReader(new FileReader(keyPath))){
            String linkKey;
            linkKey = keyReader.readLine();
            key = Integer.parseInt(linkKey);
        } catch (IOException e) {}
        return key;
    }

    public static File keyFileCreator(String keyPath,int key){
        File keyFile = new File(keyPath);
        try(BufferedWriter keyWriter = new BufferedWriter(new FileWriter(keyFile))){
            keyWriter.write(Integer.toString(key));
        } catch (IOException e) {}
        return keyFile;
    }

}
