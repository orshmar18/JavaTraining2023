
public interface EncryptionAlgorithm {
    public byte[] dataEncryption(byte[] filedata,int byteRead,Key key);
    public byte[] dataDecryption(byte[] filedata,int byteRead,Key key);

    public Key generateKey();


}
