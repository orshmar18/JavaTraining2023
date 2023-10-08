package typesOfEncryption;

import key.IKey;
import key.SimpleIKey;

import java.security.SecureRandom;

public class ShiftUpIEncryption implements IEncryptionAlgorithm {
    static final int LIMIT = 255;

    public ShiftUpIEncryption() {
    }

    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, IKey IKey) {
        byte[] bytesArrayPut = new byte[byteRead];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            bytesArrayPut[i + 1] = (byte) (value + ((SimpleIKey) IKey).getKey());
            bytesArrayPut[i] = (byte) ((value + ((SimpleIKey) IKey).getKey()) >> 8);
        }
        return bytesArrayPut;
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, IKey IKey) {
        byte[] bytesArrayPut = new byte[byteRead];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            bytesArrayPut[i + 1] = (byte) (value - ((SimpleIKey) IKey).getKey());
            bytesArrayPut[i] = (byte) ((value - ((SimpleIKey) IKey).getKey()) >> 8);
        }
        return bytesArrayPut;
    }

    public IKey generateKey() {
        SecureRandom random = new SecureRandom();
        return new SimpleIKey(random.nextInt(LIMIT));
    }
}
