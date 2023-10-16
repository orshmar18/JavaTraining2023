package test;

import javaa.key.SimpleIKey;
import javaa.typesOfEncryption.ShiftMultiplyIEncryption;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShiftMultiplyIEncryptionTest {
    static final ShiftMultiplyIEncryption shiftMultiplyIEncryption = new ShiftMultiplyIEncryption();
    static final byte[] originalData = {116, 101, 115, 116};
    static final SimpleIKey key = new SimpleIKey(797);
    static final int byteRead = 4;
    static final byte[] encryptedData = {94, 113, 112, 36};
    @Test
    public void dataEncryption() {
        byte[] afterEncryption = shiftMultiplyIEncryption.dataEncryption(originalData, byteRead, key);
        assertArrayEquals(encryptedData, afterEncryption);
    }
    @Test
    public void dataDecryption() {
        byte[] afterDecryption = shiftMultiplyIEncryption.dataDecryption(encryptedData, byteRead, key);
        assertArrayEquals(originalData, afterDecryption);
    }
    @Test
    public void devideValueKey() {
        short value = 26916;
        int after = 116;
        int afterDivide = shiftMultiplyIEncryption.devideValueKey(value,key.getKey());
        assertEquals(afterDivide,after);
    }
    @Test
    public void dataEncryptionAndDecryption() {
        byte[] afterEncryption = shiftMultiplyIEncryption.dataEncryption(originalData, byteRead, key);
        byte[] afterDecryption = shiftMultiplyIEncryption.dataDecryption(afterEncryption, byteRead, key);
        assertArrayEquals(originalData, afterDecryption);
    }
}