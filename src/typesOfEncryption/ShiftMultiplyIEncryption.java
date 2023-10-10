package typesOfEncryption;

import helpFunctions.HelpFunctions;
import key.IKey;
import key.SimpleIKey;

import java.security.SecureRandom;

public class ShiftMultiplyIEncryption implements IEncryptionAlgorithm {
    static final int LIMIT = 1000;

    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, IKey key) {
        return doEncryptionOrDecryption(filedata,byteRead,key,true);
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, IKey key) {
        return doEncryptionOrDecryption(filedata,byteRead,key,false);
    }

    public short devideValueKey(short value, int key) {//the function finds the common divisor of key and value without remainder and returns it.
        int finalvalue = value;
        while (finalvalue % key != 0) {
            finalvalue += 65536;
        }
        return (short) (finalvalue / key);
    }

    public IKey generateKey() {
        SecureRandom random = new SecureRandom();
        int[] primes = HelpFunctions.AllPrime(LIMIT);
        return new SimpleIKey(primes[random.nextInt(primes.length)]);
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
