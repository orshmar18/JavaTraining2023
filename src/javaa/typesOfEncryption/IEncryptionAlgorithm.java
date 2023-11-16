package javaa.typesOfEncryption;

import javaa.key.IKey;

public interface IEncryptionAlgorithm<T> {
     byte[] dataEncryption(byte[] fileData, int byteRead, T IKey);
     byte[] dataDecryption(byte[] fileData, int byteRead, T IKey);
     T generateKey();
     int getKeyStrength();
}
