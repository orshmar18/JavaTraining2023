package test;

import javaa.key.ComplexIKey;
import javaa.key.IKey;
import javaa.key.SimpleIKey;
import javaa.typesOfEncryption.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertArrayEquals;

@RunWith(Parameterized.class)
public class RepeatIEncryptionTest {
    private final IEncryptionAlgorithm encAlg;
    private final IKey key;
    private final byte[] encryptedData;
    RepeatIEncryption repeatIEncryption;
    static final int numberOfRepeats = 5;
    static final byte[] originalData = {116, 101, 115, 116};
    static final int byteRead = 4;

    public RepeatIEncryptionTest(IEncryptionAlgorithm encAlg, byte[] encryptedData, IKey key) {
        this.encAlg = encAlg;
        this.key = key;
        this.encryptedData = encryptedData;
        repeatIEncryption = new RepeatIEncryption(encAlg,numberOfRepeats);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCase(){
        return Arrays.asList(new Object[][]{
                {new ShiftUpIEncryption(),new byte[]{118,-17,117,-2},new SimpleIKey(130)},
                {new ShiftMultiplyIEncryption(),new byte[]{97,45,81,-108},new SimpleIKey(137)},
                {new DoubleIEncryption(new ShiftUpIEncryption(),new ShiftMultiplyIEncryption()),new byte[]{117,-89,102,14},new ComplexIKey(new SimpleIKey(130),new SimpleIKey(137))},
                {new DoubleIEncryption(new ShiftMultiplyIEncryption(),new ShiftUpIEncryption()),new byte[]{-17,87,-33,-66},new ComplexIKey(new SimpleIKey(137),new SimpleIKey(130))},
                {new DoubleIEncryption(new ShiftUpIEncryption(),new ShiftUpIEncryption()),new byte[]{119,-43,118,-28},new ComplexIKey(new SimpleIKey(130),new SimpleIKey(46))},
                {new DoubleIEncryption(new ShiftMultiplyIEncryption(),new ShiftMultiplyIEncryption()),new byte[]{-47,91,-53,-116},new ComplexIKey(new SimpleIKey(137),new SimpleIKey(7))},
        });

    }

    @Test
    public void dataEncryption() {
        byte[] afterEncryption = repeatIEncryption.dataEncryption(originalData,byteRead,key);
        assertArrayEquals(encryptedData,afterEncryption);
    }

    @Test
    public void dataDecryption() {
        byte[] afterDecryption = repeatIEncryption.dataDecryption(encryptedData,byteRead,key);
        assertArrayEquals(originalData,afterDecryption);
    }

    @Test
    public void dataEncryptionAndDecryption() {
        byte[] afterEncryption = repeatIEncryption.dataEncryption(originalData, byteRead, key);
        byte[] afterDecryption = repeatIEncryption.dataDecryption(afterEncryption, byteRead, key);
        assertArrayEquals(originalData, afterDecryption);
    }
}