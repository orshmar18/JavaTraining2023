package javaa.typesOfEncryption;

import javaa.helpFunctions.HelpFunctions;
import javaa.key.IKey;
import javaa.key.SimpleIKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;

public class ShiftMultiplyIEncryption implements IEncryptionAlgorithm {
    static final int LIMIT = 255;
    private final SimpleIKey key = new SimpleIKey(1);
    private static final Logger logger = LogManager.getLogger(ShiftMultiplyIEncryption.class);


    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, IKey key) {
        return doEncryptionOrDecryption(filedata, byteRead, key, true);
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, IKey key) {
        return doEncryptionOrDecryption(filedata, byteRead, key, false);
    }

    public short devideValueKey(short value, int key) {//the function finds the common divisor of java.key and value without remainder and returns it.
        int finalvalue = value;
        while (finalvalue % key != 0) {
            finalvalue += 65536;
        }
        return (short) (finalvalue / key);
    }

    public IKey generateKey() {
        logger.info("ShiftMultiply Generating Key");
        SecureRandom random = new SecureRandom();
        int[] primes = HelpFunctions.AllPrime(LIMIT);
        this.key.setKey(primes[random.nextInt(primes.length)]);
        return this.key;
    }

    @Override
    public int getKeyStrength() {
        return 5;
    }

    public byte[] doEncryptionOrDecryption(byte[] filedata, int byteRead, IKey key, boolean isEncryption) {
        byte[] bytesArrayPut = new byte[byteRead];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            if (isEncryption) {
                bytesArrayPut[i + 1] = (byte) (value * ((SimpleIKey) key).getKey());
                bytesArrayPut[i] = (byte) ((value * ((SimpleIKey) key).getKey()) >> 8);
            } else {
                bytesArrayPut[i + 1] = (byte) (devideValueKey(value, ((SimpleIKey) key).getKey()));
                bytesArrayPut[i] = (byte) ((devideValueKey(value, ((SimpleIKey) key).getKey())) >> 8);
            }
        }
        return bytesArrayPut;
    }

}
