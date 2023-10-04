import java.security.SecureRandom;

public class ShiftUpEncryption implements EncryptionAlgorithm {
    static final int LIMIT = 255;

    public ShiftUpEncryption() {
    }

    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, Key key) {
        byte[] bytesArrayPut = new byte[byteRead];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            bytesArrayPut[i+1] = (byte) (value +((SimpleKey)key).getKey());
            bytesArrayPut[i] = (byte) ((value +((SimpleKey)key).getKey()) >> 8);
        }
        return bytesArrayPut;
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, Key key) {
        byte[] bytesArrayPut = new byte[byteRead];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            bytesArrayPut[i+1] = (byte) (value - ((SimpleKey)key).getKey());
            bytesArrayPut[i] = (byte) ((value -((SimpleKey)key).getKey()) >> 8);
        }
        return bytesArrayPut;
    }

    public Key generateKey() {
        SecureRandom random = new SecureRandom();
        return new SimpleKey(random.nextInt(LIMIT));
    }
}
