package typesOfEncryption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonTypeName("RepeatEncryption")
public class RepeatIEncryption<T> implements IEncryptionAlgorithm<T> {
    @JsonProperty(value = "algorithm", required = true)
    private final IEncryptionAlgorithm<T> encAlg;
    @JsonProperty("time")
    private final int numberOfRepeats;
    private static final Logger logger = LogManager.getLogger(RepeatIEncryption.class);


    public RepeatIEncryption(@JsonProperty("algorithm") IEncryptionAlgorithm<T> encAlg, int numberOfRepetitions) {
        this.encAlg = encAlg;
        this.numberOfRepeats = numberOfRepetitions;
    }

    public IEncryptionAlgorithm<T> getEncAlg(){
        return encAlg;
    }

    @Override
    public byte[] dataEncryption(byte[] fileData, int byteRead, T key) {
        return doEncryptionOrDecryption(fileData,byteRead,key,true);
    }

    @Override
    public byte[] dataDecryption(byte[] fileData, int byteRead, T key) {
        return doEncryptionOrDecryption(fileData,byteRead,key,false);
    }

    @Override
    public T generateKey() {
        logger.info("RepeatEncryption Generating Key");
        return encAlg.generateKey();
    }

    @Override
    public int getKeyStrength() {
        return encAlg.getKeyStrength();
    }


    public byte[] doEncryptionOrDecryption(byte[] filedata, int byteRead, T key, boolean isEncryption) {
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
