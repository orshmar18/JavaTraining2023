package directoryProcessor;

import Utils.Pair;
import fileEncryptor.ByteBufferProcessor;
import key.IKey;
import typesOfEncryption.IEncryptionAlgorithm;

public class SyncDirectoryProcessor<T extends IKey> extends DirectoryProcessor<T> {
    public SyncDirectoryProcessor(IEncryptionAlgorithm<T> encryptionAlgorithm){super(encryptionAlgorithm);}
  @Override
    public void directoryProcess(Pair<String,String>[] pathsPairs, T key, ByteBufferProcessor<T> processorData) {
      for(Pair pathPair : pathsPairs){
            fileEncryptor.doEncryptOrDecrypt((String) pathPair.getFirst(), (String) pathPair.getSecond(), key, processorData);
        }
    }
}
