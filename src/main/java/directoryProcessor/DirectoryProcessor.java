package directoryProcessor;

import Utils.Pair;
import exception.FileNotExistsException;
import exception.InvalidEncryptionKeyException;
import exception.InvalidFilePathException;
import fileEncryptor.ByteBufferProcessor;
import fileEncryptor.FileEncryptor;
import helpFunctions.HelpFunctions;
import key.IKey;
import key.KeyHelper;
import typesOfEncryption.IEncryptionAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public abstract class DirectoryProcessor<T extends IKey> {
    private static final Logger LOGGER = LogManager.getLogger(DirectoryProcessor.class);
    protected final FileEncryptor<T> fileEncryptor;
    private final IEncryptionAlgorithm<T> encryptionAlgorithm;

    public DirectoryProcessor(IEncryptionAlgorithm<T> encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.fileEncryptor = new FileEncryptor<>(encryptionAlgorithm);
    }

    public void encryptDirectory(String originalDirectoryPathString) throws InvalidFilePathException, FileNotExistsException, IOException {
       validateDirectory(originalDirectoryPathString);
        Path originalDirectoryPath = Paths.get(originalDirectoryPathString);
        T key = encryptionAlgorithm.generateKey();
        KeyHelper.keyFileCreator(originalDirectoryPath.getParent().toString() + "\\", key);
        Path encryptedDirectoryPath = HelpFunctions.duplicateDirectory(originalDirectoryPath, "_encrypted");
        directoryEncryptionOrDecryptionProcess(originalDirectoryPath,encryptedDirectoryPath,key,encryptionAlgorithm::dataEncryption);
    }

    public void decryptDirectory(String encryptedDirectoryPathString, String keyPath) throws InvalidEncryptionKeyException, InvalidFilePathException, FileNotExistsException, IOException {
        validateDirectory(encryptedDirectoryPathString);
        this.fileEncryptor.validateKey(keyPath,encryptionAlgorithm);
        Path encryptedDirectoryPath = Paths.get(encryptedDirectoryPathString);
        Path decryptedDirectoryPath = HelpFunctions.duplicateDirectory(encryptedDirectoryPath, "_decrypted");
        T key = KeyHelper.keyFileReaderByType(encryptionAlgorithm, keyPath);
        directoryEncryptionOrDecryptionProcess(encryptedDirectoryPath,decryptedDirectoryPath,key,encryptionAlgorithm::dataDecryption);
    }

    public void directoryEncryptionOrDecryptionProcess(Path first, Path second, T key, ByteBufferProcessor<T> processorData) throws IOException {
        String[] filePathArrayEnc = arrayStringStreaming(first);
        String[] filePathArrayDec = arrayStringStreaming(second);
        Pair<String,String>[] pathsPair = new Pair[filePathArrayEnc.length];
        for (int i = 0 ; i < filePathArrayEnc.length ; i++){
            pathsPair[i] = new Pair<>(filePathArrayEnc[i],filePathArrayDec[i]);
        }
        long startTime = System.currentTimeMillis();
        directoryProcess(pathsPair, key, processorData);
        long currentTime = System.currentTimeMillis() - startTime;
            LOGGER.info("The process of the directory took " + currentTime + " milliseconds");
    }

    public String[] arrayStringStreaming(Path path){
        try(Stream<Path> s = Files.walk(path)){
            return s.filter(Files::isRegularFile).map(Path::toString).toList().toArray(new String[0]);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public abstract void directoryProcess(Pair<String,String>[] pathsPairs, T key, ByteBufferProcessor<T> processorData);

    public void validateDirectory(String directoryPath) throws FileNotExistsException, InvalidFilePathException {
        this.fileEncryptor.validatePath(directoryPath,"The path of the directory is not valid");
        File file = new File(directoryPath);
        if (!file.isDirectory()){
            throw new FileNotExistsException("the directory is not exists");
        }
    }

}
