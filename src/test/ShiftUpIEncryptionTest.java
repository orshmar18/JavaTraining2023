package test;

import static org.junit.Assert.*;
import javaa.key.*;
import javaa.typesOfEncryption.ShiftUpIEncryption;
import org.junit.Test;

public class ShiftUpIEncryptionTest {
    static final ShiftUpIEncryption shiftUpIEncryption = new ShiftUpIEncryption();
    static final byte[] originalData = {116, 101, 115, 116};
    static final IKey key = new SimpleIKey(107);
    static final int byteRead = 4;
    static final byte[] encryptedData = {116, -48, 115, -33};

    @Test
    public void dataEncryption() {
        byte[] afterEncryption = shiftUpIEncryption.dataEncryption(originalData, byteRead, key);
        assertArrayEquals(encryptedData, afterEncryption);
    }

    @Test
    public void dataDecryption() {
        byte[] afterDecryption = shiftUpIEncryption.dataDecryption(encryptedData, byteRead, key);
        assertArrayEquals(originalData, afterDecryption);
    }

    @Test
    public void dataEncryptionAndDecryption() {
        byte[] afterEncryption = shiftUpIEncryption.dataEncryption(originalData, byteRead, key);
        byte[] afterDecryption = shiftUpIEncryption.dataDecryption(afterEncryption, byteRead, key);
        assertArrayEquals(originalData, afterDecryption);
    }

}