package javaa.key;

import javaa.typesOfEncryption.DoubleIEncryption;
import javaa.typesOfEncryption.IEncryptionAlgorithm;
import javaa.typesOfEncryption.RepeatIEncryption;

import java.io.*;

public class KeyHelper {
    static final int FIRST = 0;
    static final int SECOND = 1;

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
            IKey firstIKey = new SimpleIKey(Integer.parseInt(parts[FIRST].trim()));
            IKey secondIKey = new SimpleIKey(Integer.parseInt(parts[SECOND].trim()));
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

    public static IKey keyFileReaderByType(IEncryptionAlgorithm encryptionType, String keyPath) {
        if ((encryptionType.getClass() == RepeatIEncryption.class)) {
            if (((RepeatIEncryption) encryptionType).getEncAlg().getClass() == DoubleIEncryption.class) {
                return KeyHelper.complexKeyFileReader(keyPath);
            } else {
                return KeyHelper.simpleKeyFileReader(keyPath);
            }
        } else {
            if (encryptionType.getClass() == DoubleIEncryption.class) {
                return KeyHelper.complexKeyFileReader(keyPath);
            } else {
                return KeyHelper.simpleKeyFileReader(keyPath);
            }
        }
    }
}