package javaa.typesOfEncryption;

import javaa.key.IKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RepeatIEncryption implements IEncryptionAlgorithm {
    private final IEncryptionAlgorithm encAlg;
    private final int numberOfRepeats;
    private static final Logger logger = LogManager.getLogger(RepeatIEncryption.class);


    public RepeatIEncryption(IEncryptionAlgorithm encAlg, int numberOfRepetitions) {
        this.encAlg = encAlg;
        this.numberOfRepeats = numberOfRepetitions;
    }

    public IEncryptionAlgorithm getEncAlg(){
        return encAlg;
    }

    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, IKey key) {
        return doEncryptionOrDecryption(filedata,byteRead,key,true);
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, IKey key) {
        return doEncryptionOrDecryption(filedata,byteRead,key,false);
    }

    @Override
    public IKey generateKey() {
        logger.info("RepeatEncryption Generating Key");
        return encAlg.generateKey();
    }

    @Override
    public int getKeyStrength() {
        return encAlg.getKeyStrength();
    }


    public byte[] doEncryptionOrDecryption(byte[] filedata, int byteRead, IKey key, boolean isEncryption) {
        byte[] result;
        if (isEncryption) {
            result = encAlg.dataEncryption(filedata, byteRead, key);
            for (int i = 1; i < numberOfRepeats; i++) {
                result = encAlg.dataEncryption(result, byteRead, key);
            }
        }else {
            result = encAlg.dataDecryption(filedata,byteRead, key);
            for (int i = 1 ; i < numberOfRepeats ; i++){
                result = encAlg.dataDecryption(result,byteRead, key);
            }
        }
        return result;
    }
}
