package typesOfEncryption;

import key.IKey;

public class RepeatIEncryption implements IEncryptionAlgorithm {
    private final IEncryptionAlgorithm encAlg;
    private final int numberOfRepeats;

    public RepeatIEncryption(IEncryptionAlgorithm encAlg, int numberOfRepetitions) {
        this.encAlg = encAlg;
        this.numberOfRepeats = numberOfRepetitions;
    }

    public IEncryptionAlgorithm getEncAlg(){
        return encAlg;
    }

    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, IKey IKey) {
        byte[] result;
        result = encAlg.dataEncryption(filedata,byteRead, IKey);
        for (int i = 1 ; i < numberOfRepeats ; i++){
            result = encAlg.dataEncryption(result,byteRead, IKey);
        }
        return result;
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, IKey IKey) {
        byte[] result;
        result = encAlg.dataDecryption(filedata,byteRead, IKey);
        for (int i = 1 ; i < numberOfRepeats ; i++){
            result = encAlg.dataDecryption(result,byteRead, IKey);
        }
        return result;
    }

    @Override
    public IKey generateKey() {
        return encAlg.generateKey();
    }
}
