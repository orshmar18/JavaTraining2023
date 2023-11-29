package javaa.directoryProcessor;

import javaa.exception.FileNotExistsException;
import javaa.exception.InvalidEncryptionKeyException;
import javaa.exception.InvalidFilePathException;
import javaa.helpFunctions.HelpFunctions;
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

public abstract class DirectoryProcessor<T> {
    private static final Logger logger = LogManager.getLogger(DirectoryProcessor.class);

    static final int BUFFER = 500;

    private final IEncryptionAlgorithm<T> encryptionAlgorithm;

    public DirectoryProcessor(IEncryptionAlgorithm<T> encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public void encryptDirectory(String originalDirectoryPathString) throws InvalidFilePathException, FileNotExistsException, IOException {
        Path originalDirectoryPath = Paths.get(originalDirectoryPathString);
        T key = encryptionAlgorithm.generateKey();
        KeyHelper.keyFileCreator(originalDirectoryPath.getParent().toString() + "\\", key);
        Path encryptedDirectoryPath = HelpFunctions.duplicateDirectory(originalDirectoryPath, "_encrypted");

        List<String> filePaths = Files.walk(originalDirectoryPath).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
        String[] filePathArray = filePaths.toArray(new String[0]);
        List<String> filePathsEnc = Files.walk(encryptedDirectoryPath).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
        String[] filePathEncArray = filePathsEnc.toArray(new String[0]);
        directoryProcess(filePathArray, filePathEncArray, key, true);
    }

    public void decryptDirectory(String encryptedDirectoryPathString, String keyPath) throws InvalidEncryptionKeyException,InvalidFilePathException, FileNotExistsException, IOException {
        Path encryptedDirectoryPath = Paths.get(encryptedDirectoryPathString);
        Path decryptedDirectoryPath = HelpFunctions.duplicateDirectory(encryptedDirectoryPath, "_decrypted");
        T key = KeyHelper.keyFileReaderByType(encryptionAlgorithm, keyPath);
        List<String> filePathsEnc = Files.walk(encryptedDirectoryPath).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
        String[] filePathArray = filePathsEnc.toArray(new String[0]);
        List<String> filePathsDec = Files.walk(decryptedDirectoryPath).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
        String[] filePathEncArray = filePathsDec.toArray(new String[0]);
        directoryProcess(filePathArray, filePathEncArray, key, false);
    }

    public void directoryProcess(String[] fileToReadPath, String[] fileToWriteInPath, T key, boolean isEncryption) {
        for (int i = 0; i < fileToReadPath.length; i++) {
            doEncryptOrDecrypt(fileToReadPath[i], fileToWriteInPath[i], key, isEncryption);
        }
    }


    public void doEncryptOrDecrypt(String filePathToRead, String filePathToWrite, T key, boolean isEncryption) {
        try (FileInputStream fileInputStream = new FileInputStream(filePathToRead);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(filePathToWrite);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            while (dataInputStream.available() > 0) {
                byte[] fileData = new byte[BUFFER];
                int bytesRead = dataInputStream.read(fileData, 0, BUFFER);
                byte[] bytesToWrite;
                if (isEncryption) {
                    bytesToWrite = encryptionAlgorithm.dataEncryption(fileData, bytesRead, key);
                } else {
                    bytesToWrite = encryptionAlgorithm.dataDecryption(fileData, bytesRead, key);
                }
                dataOutputStream.write(bytesToWrite);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

}
