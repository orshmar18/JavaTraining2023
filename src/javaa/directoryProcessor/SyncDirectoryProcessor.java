package javaa.directoryProcessor;

import javaa.key.IKey;
import javaa.typesOfEncryption.IEncryptionAlgorithm;

public class SyncDirectoryProcessor<T extends IKey> extends DirectoryProcessor<T> {
    public SyncDirectoryProcessor(IEncryptionAlgorithm<T> encryptionAlgorithm){super(encryptionAlgorithm);}
  @Override
    public void directoryProcess(String[] fileToReadPath, String[] fileToWriteInPath, T key, boolean isEncryption) {
        for (int i = 0; i < fileToReadPath.length; i++) {
            fileEncryptor.doEncryptOrDecrypt(fileToReadPath[i], fileToWriteInPath[i], key, isEncryption);
        }
    }
}
