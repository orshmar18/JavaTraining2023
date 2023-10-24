package test;

import javaa.exception.FileNotExistsException;
import javaa.exception.InvalidEncryptionKeyException;
import javaa.exception.InvalidFilePathException;
import javaa.fileEncryptor.FileEncryptor;
import javaa.typesOfEncryption.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)

public class FileEncryptorTest {

    private final FileEncryptor fileEncryptor;


    static final String wrongFilePath = "Worng Path";
    static final String notExistsFilePath = "C:\\try\\orsadasjd";

    static final String correctKeyPath = "C:\\try\\Junit\\Junit_key.txt";
    static final String correctPath = "C:\\try\\Junit\\Junit.txt";
    static final String wrongKeyPath = "Worng Path";
    static final String wrongKeyValue = "C:\\try\\Junit\\Junit_wrong_key.txt";
    static final int numberOfRepeats = 5;

    public FileEncryptorTest(IEncryptionAlgorithm encryptionAlgorithm) {
        this.fileEncryptor = new FileEncryptor(encryptionAlgorithm);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCase() {
        return Arrays.asList(new Object[][]{
                {new ShiftUpIEncryption()},
                {new ShiftMultiplyIEncryption()},
                {new DoubleIEncryption(new ShiftUpIEncryption(), new ShiftMultiplyIEncryption())},
                {new DoubleIEncryption(new ShiftMultiplyIEncryption(), new ShiftUpIEncryption())},
                {new DoubleIEncryption(new ShiftUpIEncryption(), new ShiftUpIEncryption())},
                {new DoubleIEncryption(new ShiftMultiplyIEncryption(), new ShiftMultiplyIEncryption())},
                {new RepeatIEncryption(new ShiftUpIEncryption(), numberOfRepeats)},
                {new RepeatIEncryption(new ShiftMultiplyIEncryption(), numberOfRepeats)},
                {new RepeatIEncryption(new DoubleIEncryption(new ShiftUpIEncryption(), new ShiftMultiplyIEncryption()), numberOfRepeats)},
                {new RepeatIEncryption(new DoubleIEncryption(new ShiftMultiplyIEncryption(), new ShiftUpIEncryption()), numberOfRepeats)},
                {new RepeatIEncryption(new DoubleIEncryption(new ShiftUpIEncryption(), new ShiftUpIEncryption()), numberOfRepeats)},
                {new RepeatIEncryption(new DoubleIEncryption(new ShiftMultiplyIEncryption(), new ShiftMultiplyIEncryption()), numberOfRepeats)},
        });

    }


    @Test
    public void EncryptWithInvalidPathTest() {
        assertThrows(InvalidFilePathException.class, ()->fileEncryptor.encryptFile(wrongFilePath));
    }
    @Test
    public void EncryptionWithFileNotExistsTest() {
        assertThrows(FileNotExistsException.class, ()->fileEncryptor.encryptFile(notExistsFilePath));
    }


@Test
    public void DecryptionFilePathExceptionTest() {
        assertThrows(InvalidFilePathException.class, ()->fileEncryptor.decryptFile(wrongFilePath, correctKeyPath));
    }
    @Test
    public void DecryptionFileNotExistsExceptionTest() {
        assertThrows(FileNotExistsException.class, ()->fileEncryptor.decryptFile(notExistsFilePath, correctKeyPath));
    }
    @Test
    public void DecryptionInvalidKeyPathExceptionTest() {
        assertThrows(InvalidFilePathException.class, ()->fileEncryptor.decryptFile(correctPath, wrongKeyPath));
    }
    @Test
    public void DecryptionFileOfKeyNotExistsExceptionTest() {
        assertThrows(FileNotExistsException.class, ()->fileEncryptor.decryptFile(correctPath, notExistsFilePath));
    }
    @Test
    public void DecryptionKeyInvalidExceptionTest() {
        assertThrows(InvalidEncryptionKeyException.class, ()->fileEncryptor.decryptFile(correctPath, wrongKeyValue));
    }
}