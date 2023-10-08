package typesOfEncryption;

import helpFunctions.HelpFunctions;
import key.IKey;
import key.SimpleIKey;

import java.security.SecureRandom;

public class ShiftMultiplyIEncryption implements IEncryptionAlgorithm {
    static final int LIMIT = 1000;

    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, IKey IKey) {
        byte[] bytesArrayPut = new byte[byteRead];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            bytesArrayPut[i + 1] = (byte) (value * ((SimpleIKey) IKey).getKey());
            bytesArrayPut[i] = (byte) ((value * ((SimpleIKey) IKey).getKey()) >> 8);
        }
        return bytesArrayPut;
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, IKey IKey) {
        byte[] bytesArrayPut = new byte[byteRead];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            bytesArrayPut[i + 1] = (byte) (devideValueKey(value, ((SimpleIKey) IKey).getKey()));
            bytesArrayPut[i] = (byte) ((devideValueKey(value, ((SimpleIKey) IKey).getKey())) >> 8);
        }
        return bytesArrayPut;
    }

    public short devideValueKey(short value, int key) {
        int v1 = value;
        while (v1 % key != 0) {
            v1 += 65536;
        }
        return (short) (v1 / key);
    }

    public IKey generateKey() {
        SecureRandom random = new SecureRandom();
        int[] primes = HelpFunctions.AllPrime(LIMIT);
        return new SimpleIKey(primes[random.nextInt(primes.length)]);
    }
}
