import java.security.SecureRandom;

public class ShiftUpEncryption implements EncryptionAlgorithm {
    static final int LIMIT = 255;


    @Override
    public String dataEncryption(byte[] filedata, int byteRead, int key) {
        char[] charArrayPut = new char[byteRead / 2];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            charArrayPut[i / 2] = (char) (value + key);
        }
        return new String(charArrayPut);
    }

    @Override
    public String dataDecryption(byte[] filedata, int byteRead, int key) {
        char[] charArrayPut = new char[byteRead / 2];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            charArrayPut[i / 2] = (char) (value - key);
        }
        return new String(charArrayPut);
    }

    @Override
    public int generateKey(SecureRandom random) {
        return random.nextInt(LIMIT);
    }
}
