import java.security.SecureRandom;

public class ShiftMultiplyEncryption implements EncryptionAlgorithm{
    static final int LIMIT = 1000;


    @Override
    public String dataEncryption(byte[] filedata, int byteRead, int key) {
        char[] charArrayPut = new char[byteRead / 2];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            charArrayPut[i / 2] = (char) (value * key);
        }
        return new String(charArrayPut);
    }

    @Override
    public String dataDecryption(byte[] filedata, int byteRead, int key) {
        char[] charArrayPut = new char[byteRead / 2];
        for (int i = 0; i < byteRead; i += 2) {
            short value = (short) ((filedata[i] << 8) | (filedata[i + 1] & 0xFF));
            charArrayPut[i / 2] = (char) (devideValueKey(value,key));
        }
        return new String(charArrayPut);
    }

    public short devideValueKey(short value, int key){
        int v1 = value;
        while (v1 % key != 0){
            v1 += 65536;
        }
        return (short) (v1 / key);
    }

    @Override
    public int generateKey(SecureRandom random) {
        int[] primes = HelpFunctions.AllPrime(LIMIT);
        return primes[random.nextInt(primes.length)];
    }
}
