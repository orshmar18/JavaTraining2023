package typesOfEncryption;

import key.ComplexIKey;
import key.IKey;

public class DoubleIEncryption implements IEncryptionAlgorithm {
    private final IEncryptionAlgorithm encAlg1;
    private final IEncryptionAlgorithm encAlg2;

    public DoubleIEncryption(IEncryptionAlgorithm encAlg1, IEncryptionAlgorithm encAlg2) {
        this.encAlg1 = encAlg1;
        this.encAlg2 = encAlg2;
    }

    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, IKey IKey) {
        IKey[] IKeys = ((ComplexIKey) IKey).getComplex();
        return encAlg2.dataEncryption(encAlg1.dataEncryption(filedata,byteRead, IKeys[0]),byteRead, IKeys[1]);
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, IKey IKey) {
        IKey[] IKeys = ((ComplexIKey) IKey).getComplex();
        byte[] firstEnc = encAlg2.dataDecryption(filedata,byteRead, IKeys[1]);
        return encAlg1.dataDecryption(firstEnc,byteRead, IKeys[0]);
    }

    @Override
    public IKey generateKey() {
        IKey firstEncryptionIKey = encAlg1.generateKey();
        IKey secondEncryptionIKey =encAlg2.generateKey();
        return new ComplexIKey(firstEncryptionIKey, secondEncryptionIKey);
    }
}
