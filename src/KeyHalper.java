import javax.xml.stream.events.DTD;
import java.io.*;
import java.security.SecureRandom;

public class KeyHalper {


    public static Key simpleKeyFileReader(String keyPath){
        SimpleKey key = new SimpleKey(0);
        try(BufferedReader keyReader = new BufferedReader(new FileReader(keyPath))){
            String linkKey;
            linkKey = keyReader.readLine();
            key.setKey(Integer.parseInt(linkKey));
        } catch (IOException e) {}
        return key;
    }

    public static Key complexKeyFileReader(String keyPath){
        ComplexKey key = new ComplexKey();
        try(BufferedReader keyReader = new BufferedReader(new FileReader(keyPath))){
            String linkKey;
            linkKey = keyReader.readLine();
            String[] parts = linkKey.substring(1,linkKey.length() - 1).split(",");
            Key a = new SimpleKey(Integer.parseInt(parts[0].trim()));
            Key b = new SimpleKey(Integer.parseInt(parts[1].trim()));
            key.setComplex(a,b);
        } catch (IOException e) {}
        return key;
    }



    public static File keyFileCreator(String path, Key key){
        String fileKeyName = path + "_key.txt";
        File keyFile = new File(fileKeyName);
        try(BufferedWriter keyWriter = new BufferedWriter(new FileWriter(keyFile))){
            keyWriter.write(key.toString());
        } catch (IOException e) {}
        return keyFile;
    }

}
