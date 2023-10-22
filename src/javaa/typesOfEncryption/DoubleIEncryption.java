package javaa.typesOfEncryption;

import javaa.comperator.IEncryptionAlgorithmComparator;
import javaa.exception.InvalidEncryptionKeyException;
import javaa.key.ComplexIKey;
import javaa.key.IKey;
import javaa.key.SimpleIKey;

public class DoubleIEncryption implements IEncryptionAlgorithm {
    private final IEncryptionAlgorithm encAlg1;
    private final IEncryptionAlgorithm encAlg2;
    static final int FIRST = 0;
    static final int SECOND = 1;

    private final ComplexIKey key = new ComplexIKey();

    public IEncryptionAlgorithm getEncAlg1() {
        return encAlg1;
    }

    public IEncryptionAlgorithm getEncAlg2() {
        return encAlg2;
    }

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
        try {
            this.key.setComplex(encAlg1.generateKey(), encAlg2.generateKey());
        } catch (InvalidEncryptionKeyException e) {
            System.out.println(e);
        }
        return new ComplexIKey(this.key.getComplex()[0], this.key.getComplex()[1]);
    }

    @Override
    public int getKeyStrength() {
        IEncryptionAlgorithmComparator comparator = new IEncryptionAlgorithmComparator();
        return comparator.compare(encAlg1,encAlg2);
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
