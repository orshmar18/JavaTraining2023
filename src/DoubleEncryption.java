import java.security.SecureRandom;

public class DoubleEncryption implements EncryptionAlgorithm{
    @Override
    public String dataEncryption(byte[] filedata, int byteRead, int key) {
        return null;
    }

    @Override
    public String dataDecryption(byte[] filedata, int byteRead, int key) {
        return null;
    }

    @Override
    public int generateKey(SecureRandom random) {
        return 0;
    }
}
