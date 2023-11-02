package javaa.typesOfEncryption;

import javaa.key.IKey;
import javaa.key.SimpleIKey;

import java.security.SecureRandom;


public class ShiftUpIEncryption implements IEncryptionAlgorithm {
    static final int LIMIT = 32767;
    private final SimpleIKey key = new SimpleIKey(1);


    public ShiftUpIEncryption() {
    }

    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, IKey key) {
        return doEncryptionOrDecryption(filedata,byteRead,key,true);
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, IKey key) {
        return doEncryptionOrDecryption(filedata,byteRead,key,false);
    }

    public IKey generateKey() {
        SecureRandom random = new SecureRandom();
        this.key.setKey(random.nextInt(LIMIT));
        return this.key;
    }

    @Override
    public int getKeyStrength() {
        return 3;
    }

    public byte[] doEncryptionOrDecryption(byte[] filedata, int byteRead, IKey key, boolean isEncryption) {
        byte[] bytesArrayPut = new byte[byteRead];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            if (isEncryption) {
                bytesArrayPut[i + 1] = (byte) (value + ((SimpleIKey) key).getKey());
                bytesArrayPut[i] = (byte) ((value + ((SimpleIKey) key).getKey()) >> 8);
            } else {
                bytesArrayPut[i + 1] = (byte) (value - ((SimpleIKey) key).getKey());
                bytesArrayPut[i] = (byte) ((value - ((SimpleIKey) key).getKey()) >> 8);
            }
        }
        return bytesArrayPut;
    }
}
