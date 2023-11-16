package javaa.typesOfEncryption;

import javaa.comperator.IEncryptionAlgorithmComparator;
import javaa.key.ComplexIKey;
import javaa.key.IKey;
import javaa.key.SimpleIKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DoubleIEncryption implements IEncryptionAlgorithm<ComplexIKey<SimpleIKey<Integer>>> {
    private final IEncryptionAlgorithm<SimpleIKey<Integer>> encAlg1;
    private final IEncryptionAlgorithm<SimpleIKey<Integer>> encAlg2;
    static final int FIRST = 0;
    static final int SECOND = 1;

    private final ComplexIKey key = new ComplexIKey();
    private static final Logger logger = LogManager.getLogger(DoubleIEncryption.class);

    public IEncryptionAlgorithm<SimpleIKey<Integer>> getEncAlg1() {
        return encAlg1;
    }

    public IEncryptionAlgorithm<SimpleIKey<Integer>> getEncAlg2() {
        return encAlg2;
    }

    public DoubleIEncryption(IEncryptionAlgorithm<SimpleIKey<Integer>> encAlg1, IEncryptionAlgorithm<SimpleIKey<Integer>> encAlg2) {
        this.encAlg1 = encAlg1;
        this.encAlg2 = encAlg2;
    }

    @Override
    public byte[] dataEncryption(byte[] fileData, int byteRead, ComplexIKey<SimpleIKey<Integer>> key) {
        return doEncryptionOrDecryption(fileData, byteRead, key, true);
    }

    @Override
    public byte[] dataDecryption(byte[] fileData, int byteRead, ComplexIKey<SimpleIKey<Integer>> key) {
        return doEncryptionOrDecryption(fileData, byteRead, key, false);
    }

    @Override
    public ComplexIKey<SimpleIKey<Integer>> generateKey() {
        logger.info("DoubleEncryption Generating Key");
        SimpleIKey<Integer> firstKey = encAlg1.generateKey();
        SimpleIKey<Integer> secondKey = encAlg2.generateKey();
        return new ComplexIKey<>(firstKey,secondKey);
    }

    @Override
    public int getKeyStrength() {
        IEncryptionAlgorithmComparator comparator = new IEncryptionAlgorithmComparator();
        return comparator.compare(encAlg1,encAlg2);
    }

    public byte[] doEncryptionOrDecryption(byte[] fileData, int byteRead, ComplexIKey<SimpleIKey<Integer>> key, boolean isEncryption) {
        if (isEncryption) {
            return encAlg2.dataEncryption(encAlg1.dataEncryption(fileData, byteRead,key.getFirst()), byteRead, key.getSecond());
        } else {
            return encAlg1.dataDecryption(encAlg2.dataDecryption(fileData, byteRead, key.getSecond()), byteRead, key.getFirst());
        }
    }
}
