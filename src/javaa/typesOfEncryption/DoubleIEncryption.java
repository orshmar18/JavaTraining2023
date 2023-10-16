package javaa.typesOfEncryption;

import javaa.key.ComplexIKey;
import javaa.key.IKey;
import javaa.key.SimpleIKey;

public class DoubleIEncryption implements IEncryptionAlgorithm {
    private final IEncryptionAlgorithm encAlg1;
    private final IEncryptionAlgorithm encAlg2;
    static final int FIRST = 0;
    static final int SECOND = 1;

    public DoubleIEncryption(IEncryptionAlgorithm encAlg1, IEncryptionAlgorithm encAlg2) {
        this.encAlg1 = encAlg1;
        this.encAlg2 = encAlg2;
    }

    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, IKey key) {
        return doEncryptionOrDecryption(filedata, byteRead, key, true);
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, IKey key) {
        return doEncryptionOrDecryption(filedata, byteRead, key, false);
    }

    @Override
    public IKey generateKey() {
        IKey firstEncryptionIKey = encAlg1.generateKey();
        IKey secondEncryptionIKey = encAlg2.generateKey();
        return new ComplexIKey(firstEncryptionIKey, secondEncryptionIKey);
    }

    public byte[] doEncryptionOrDecryption(byte[] filedata, int byteRead, IKey key, boolean isEncryption) {
        IKey[] IKeys = ((ComplexIKey) key).getComplex();
        if (isEncryption) {
            return encAlg2.dataEncryption(encAlg1.dataEncryption(filedata, byteRead, IKeys[FIRST]), byteRead, IKeys[SECOND]);
        } else {
            return encAlg1.dataDecryption(encAlg2.dataDecryption(filedata, byteRead, IKeys[SECOND]), byteRead, IKeys[FIRST]);
        }
    }
}
