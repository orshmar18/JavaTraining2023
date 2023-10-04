import java.security.SecureRandom;

public class DoubleEncryption implements EncryptionAlgorithm{
    private final EncryptionAlgorithm encAlg1;
    private final EncryptionAlgorithm encAlg2;

    public DoubleEncryption(EncryptionAlgorithm encAlg1, EncryptionAlgorithm encAlg2) {
        this.encAlg1 = encAlg1;
        this.encAlg2 = encAlg2;
    }

    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, Key key) {
        Key[] k = ((ComplexKey)key).getComplex();
        return encAlg2.dataEncryption(encAlg1.dataEncryption(filedata,byteRead,k[0]),byteRead,k[1]);
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, Key key) {
        Key[] k = ((ComplexKey)key).getComplex();
        byte[] firstEnc = encAlg2.dataDecryption(filedata,byteRead,k[1]);
        return encAlg1.dataDecryption(firstEnc,byteRead,k[0]);
    }

    @Override
    public Key generateKey() {
        Key k1 = encAlg1.generateKey();
        Key k2  =encAlg2.generateKey();
        return new ComplexKey(k1,k2);
    }
}
