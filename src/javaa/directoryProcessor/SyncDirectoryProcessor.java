package javaa.directoryProcessor;

import javaa.Utils.Pair;
import javaa.key.IKey;
import javaa.typesOfEncryption.IEncryptionAlgorithm;

public class SyncDirectoryProcessor<T extends IKey> extends DirectoryProcessor<T> {
    public SyncDirectoryProcessor(IEncryptionAlgorithm<T> encryptionAlgorithm){super(encryptionAlgorithm);}
  @Override
    public void directoryProcess(Pair[] pathsPairs, T key, boolean isEncryption) {
      for(Pair pathPair : pathsPairs){
            fileEncryptor.doEncryptOrDecrypt((String) pathPair.getFirst(), (String) pathPair.getSecond(), key, isEncryption);
        }
    }
}
