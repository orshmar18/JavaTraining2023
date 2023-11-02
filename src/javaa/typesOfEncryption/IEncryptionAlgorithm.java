package javaa.typesOfEncryption;

import javaa.key.IKey;

public interface IEncryptionAlgorithm {
     byte[] dataEncryption(byte[] filedata, int byteRead, IKey IKey); // Why array and not list?
     byte[] dataDecryption(byte[] filedata, int byteRead, IKey IKey);
     IKey generateKey();
     int getKeyStrength();
}
