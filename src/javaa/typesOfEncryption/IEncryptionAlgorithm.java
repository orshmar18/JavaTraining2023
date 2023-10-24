package javaa.typesOfEncryption;

import javaa.exception.InvalidEncryptionKeyException;
import javaa.key.IKey;

public interface IEncryptionAlgorithm {
     byte[] dataEncryption(byte[] filedata, int byteRead, IKey IKey);
     byte[] dataDecryption(byte[] filedata, int byteRead, IKey IKey);
     IKey generateKey();
     int getKeyStrength();
}
