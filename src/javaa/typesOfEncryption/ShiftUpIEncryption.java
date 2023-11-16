package javaa.typesOfEncryption;

import javaa.fileEncryptor.FileEncryptor;
import javaa.key.IKey;
import javaa.key.SimpleIKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;


public class ShiftUpIEncryption implements IEncryptionAlgorithm<SimpleIKey<Integer>> {
    static final int LIMIT = 32767;
    private final SimpleIKey key = new SimpleIKey(1);
    private static final Logger logger = LogManager.getLogger(ShiftUpIEncryption.class);



    public ShiftUpIEncryption() {
    }

    @Override
    public byte[] dataEncryption(byte[] fileData, int byteRead, SimpleIKey<Integer> key) {
        return doEncryptionOrDecryption(fileData,byteRead,key,true);
    }

    @Override
    public byte[] dataDecryption(byte[] fileData, int byteRead, SimpleIKey<Integer> key) {
        return doEncryptionOrDecryption(fileData,byteRead,key,false);
    }

    public SimpleIKey<Integer> generateKey() {
        logger.info("ShiftUp Generating Key");
        SecureRandom random = new SecureRandom();
        this.key.setKey(random.nextInt(LIMIT));
        return this.key;
    }

    @Override
    public int getKeyStrength() {
        return 3;
    }

    public byte[] doEncryptionOrDecryption(byte[] fileData, int byteRead, SimpleIKey<Integer> key, boolean isEncryption) {
        byte[] bytesArrayPut = new byte[byteRead];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((fileData[i] << 8) | (fileData[i + 1] & 0xFF));
            if (isEncryption) {
                bytesArrayPut[i + 1] = (byte) (value + key.getKey());
                bytesArrayPut[i] = (byte) ((value +  key.getKey()) >> 8);
            } else {
                bytesArrayPut[i + 1] = (byte) (value -  key.getKey());
                bytesArrayPut[i] = (byte) ((value -  key.getKey()) >> 8);
            }
        }
        return bytesArrayPut;
    }
}
