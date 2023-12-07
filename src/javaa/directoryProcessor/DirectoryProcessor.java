package javaa.directoryProcessor;

import javaa.Utils.Pair;
import javaa.exception.FileNotExistsException;
import javaa.exception.InvalidEncryptionKeyException;
import javaa.exception.InvalidFilePathException;
import javaa.fileEncryptor.FileEncryptor;
import javaa.helpFunctions.HelpFunctions;
import javaa.key.IKey;
import javaa.key.KeyHelper;
import javaa.typesOfEncryption.IEncryptionAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DirectoryProcessor<T extends IKey> {
    private static final Logger LOGGER = LogManager.getLogger(DirectoryProcessor.class);

    static final int BUFFER = 8000;
    protected final FileEncryptor<T> fileEncryptor;
    private final IEncryptionAlgorithm<T> encryptionAlgorithm;

    public DirectoryProcessor(IEncryptionAlgorithm<T> encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.fileEncryptor = new FileEncryptor<>(encryptionAlgorithm);
    }

    public void encryptDirectory(String originalDirectoryPathString) throws InvalidFilePathException, FileNotExistsException, IOException {
        Path originalDirectoryPath = Paths.get(originalDirectoryPathString);
        T key = encryptionAlgorithm.generateKey();
        KeyHelper.keyFileCreator(originalDirectoryPath.getParent().toString() + "\\", key);
        Path encryptedDirectoryPath = HelpFunctions.duplicateDirectory(originalDirectoryPath, "_encrypted");
        directoryEncryptionOrDecryptionProcess(originalDirectoryPath,encryptedDirectoryPath,key,true);

//        List<String> filePaths = Files.walk(originalDirectoryPath).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
//        String[] filePathArray = filePaths.toArray(new String[0]);
//        List<String> filePathsEnc = Files.walk(encryptedDirectoryPath).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
//        String[] filePathEncArray = filePathsEnc.toArray(new String[0]);
//        directoryProcess(filePathArray, filePathEncArray, key, true);
    }

    public void decryptDirectory(String encryptedDirectoryPathString, String keyPath) throws InvalidEncryptionKeyException, InvalidFilePathException, FileNotExistsException, IOException {
        Path encryptedDirectoryPath = Paths.get(encryptedDirectoryPathString);
        Path decryptedDirectoryPath = HelpFunctions.duplicateDirectory(encryptedDirectoryPath, "_decrypted");
        T key = KeyHelper.keyFileReaderByType(encryptionAlgorithm, keyPath);
        directoryEncryptionOrDecryptionProcess(encryptedDirectoryPath,decryptedDirectoryPath,key,false);

//        List<String> filePathsEnc = Files.walk(encryptedDirectoryPath).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
//        String[] filePathArray = filePathsEnc.toArray(new String[0]);
//        List<String> filePathsDec = Files.walk(decryptedDirectoryPath).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
//        String[] filePathEncArray = filePathsDec.toArray(new String[0]);
//        directoryProcess(filePathArray, filePathEncArray, key, false);
    }

    public void directoryEncryptionOrDecryptionProcess(Path first, Path second, T key, boolean isEncryption) throws IOException {
        List<String> filePathsEnc = Files.walk(first).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
        String[] filePathArrayEnc = filePathsEnc.toArray(new String[0]);
        List<String> filePathsDec = Files.walk(second).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
        String[] filePathArrayDec = filePathsDec.toArray(new String[0]);
        Pair<String,String>[] pathsPair = new Pair[filePathArrayEnc.length];
        for (int i = 0 ; i < filePathArrayEnc.length ; i++){
            pathsPair[i] = new Pair<>(filePathArrayEnc[i],filePathArrayDec[i]);
        }
        long startTime = System.currentTimeMillis();
        directoryProcess(pathsPair, key, isEncryption);
        long currentTime = System.currentTimeMillis() - startTime;
        if (isEncryption)
            LOGGER.info("The encryption of the directory took " + currentTime + " milliseconds");
        else
            LOGGER.info("The decryption of the directory took " + currentTime + " milliseconds");

    }

    public abstract void directoryProcess(Pair[] pathsPairs, T key, boolean isEncryption);


}
