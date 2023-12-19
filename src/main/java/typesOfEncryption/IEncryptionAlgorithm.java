package typesOfEncryption;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DoubleIEncryption.class,name = "DoubleEncryption"),
        @JsonSubTypes.Type(value = RepeatIEncryption.class,name = "RepeatEncryption"),
        @JsonSubTypes.Type(value = ShiftMultiplyIEncryption.class,name = "ShiftMultiplyEncryption"),
        @JsonSubTypes.Type(value = ShiftUpIEncryption.class,name = "ShiftUpEncryption"),
})


public interface IEncryptionAlgorithm<T> {
     byte[] dataEncryption(byte[] fileData, int byteRead, T IKey);
     byte[] dataDecryption(byte[] fileData, int byteRead, T IKey);
     T generateKey();
     int getKeyStrength();
}
