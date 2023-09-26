import java.security.SecureRandom;

public interface EncryptionAlgorithm {
    public String dataEncryption(byte[] filedata,int byteRead,int key);
    public String dataDecryption(byte[] filedata,int byteRead,int key);

    public int generateKey(SecureRandom random);


}
