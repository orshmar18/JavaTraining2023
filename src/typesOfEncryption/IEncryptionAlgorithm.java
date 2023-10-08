package typesOfEncryption;

import key.IKey;

public interface IEncryptionAlgorithm {
    public byte[] dataEncryption(byte[] filedata, int byteRead, IKey IKey);
    public byte[] dataDecryption(byte[] filedata, int byteRead, IKey IKey);

    public IKey generateKey();
}
