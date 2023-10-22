package test;

import javaa.exception.InvalidEncryptionKeyException;
import javaa.exception.InvalidPathException;
import javaa.fileEncryptor.FileEncryptor;
import javaa.key.ComplexIKey;
import javaa.key.SimpleIKey;
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

    static final String correctPath = "C:\\try\\or_shmaryahu.txt\n";
    static final String worngPath = "C:\\try\\or_shmaryahu.txt\n";
    static final String correctKeyPath = "C:\\try\\or_shmaryahu.txt\n";
    static final String worngKeyPath = "Worng Path";
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
    public void DecryptionKeyExceptionTest() {
        assertThrows(InvalidEncryptionKeyException.class, ()->fileEncryptor.decryptFile(correctPath, worngKeyPath));
    }

    @Test
    public void EncryptWithInvalidPath() {
        assertThrows(InvalidPathException.class, ()->fileEncryptor.encryptFile(correctPath));
    }

    @Test
    public void writeOrReadFromFile() {
    }
}