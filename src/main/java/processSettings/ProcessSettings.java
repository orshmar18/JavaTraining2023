package processSettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import typesOfEncryption.IEncryptionAlgorithm;

public class ProcessSettings {
    @JsonProperty(value = "originalPath",required = true)
    public String originalPath;
    @JsonProperty(value = "encryptedPath",required = true)
    public String encryptedPath;
    @JsonProperty(value = "keyPath",required = true)
    public String keyPath;
    @JsonProperty(value = "algorithm",required = true)
    public IEncryptionAlgorithm<?> encryptionAlgorithm;
}
