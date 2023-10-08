package key;

import java.io.*;

public class KeyHelper {

    public static IKey simpleKeyFileReader(String keyPath) {
        SimpleIKey key = new SimpleIKey(0);
        try (BufferedReader keyReader = new BufferedReader(new FileReader(keyPath))) {
            String linkKey;
            linkKey = keyReader.readLine();
            key.setKey(Integer.parseInt(linkKey));
        } catch (IOException e) {
        }
        return key;
    }

    public static IKey complexKeyFileReader(String keyPath) {
        ComplexIKey key = new ComplexIKey();
        try (BufferedReader keyReader = new BufferedReader(new FileReader(keyPath))) {
            String linkKey;
            linkKey = keyReader.readLine();
            String[] parts = linkKey.substring(1, linkKey.length() - 1).split(",");
            IKey firstIKey = new SimpleIKey(Integer.parseInt(parts[0].trim()));
            IKey secondIKey = new SimpleIKey(Integer.parseInt(parts[1].trim()));
            key.setComplex(firstIKey, secondIKey);
        } catch (IOException e) {
        }
        return key;
    }

    public static File keyFileCreator(String path, IKey IKey) {
        String fileKeyName = path + "_key.txt";
        File keyFile = new File(fileKeyName);
        try (BufferedWriter keyWriter = new BufferedWriter(new FileWriter(keyFile))) {
            keyWriter.write(IKey.toString());
        } catch (IOException e) {
        }
        return keyFile;
    }
}