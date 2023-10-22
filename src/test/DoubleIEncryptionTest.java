package test;

import org.junit.Test;
import javaa.key.*;
import javaa.typesOfEncryption.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class DoubleIEncryptionTest {
    private final IEncryptionAlgorithm encAlg1;
    private final IEncryptionAlgorithm encAlg2;
    private final byte[] encryptedData;
    private final ComplexIKey key;
    DoubleIEncryption doubleIEncryption;
    static final byte[] originalData = {116, 101, 115, 116};
    static final int byteRead = 4;


    public DoubleIEncryptionTest(IEncryptionAlgorithm encAlg1, IEncryptionAlgorithm encAlg2, byte[] encryptedData, ComplexIKey key) {
        this.encAlg1 = encAlg1;
        this.encAlg2 = encAlg2;
        doubleIEncryption = new DoubleIEncryption(encAlg1, encAlg2);
        this.encryptedData = encryptedData;
        this.key = key;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCase() {
        return Arrays.asList(new Object[][]{
                {new ShiftUpIEncryption(), new ShiftMultiplyIEncryption(), new byte[]{-49, 91, -62, -2}, new ComplexIKey(new SimpleIKey(66), new SimpleIKey(557))},
                {new ShiftMultiplyIEncryption(), new ShiftUpIEncryption(), new byte[]{64, 3, 51, -90}, new ComplexIKey(new SimpleIKey(557), new SimpleIKey(66))},
                {new ShiftUpIEncryption(), new ShiftUpIEncryption(), new byte[]{116, -69, 115, -54}, new ComplexIKey(new SimpleIKey(20), new SimpleIKey(66))},
                {new ShiftMultiplyIEncryption(), new ShiftMultiplyIEncryption(), new byte[]{-66, 71, 103, -68}, new ComplexIKey(new SimpleIKey(7), new SimpleIKey(557))},
        });
    }

    @Test
    public void dataEncryption() {
        byte[] afterEncryption = doubleIEncryption.dataEncryption(originalData, byteRead, key);
        assertArrayEquals(encryptedData, afterEncryption);
    }

    @Test
    public void dataDecryption() {
        byte[] afterDecryption = doubleIEncryption.dataDecryption(encryptedData, byteRead, key);
        assertArrayEquals(originalData, afterDecryption);
    }

    @Test
    public void dataEncryptionAndDecryption() {
        byte[] afterEncryption = doubleIEncryption.dataEncryption(originalData, byteRead, key);
        byte[] afterDecryption = doubleIEncryption.dataDecryption(afterEncryption, byteRead, key);
        assertArrayEquals(originalData, afterDecryption);
    }


}