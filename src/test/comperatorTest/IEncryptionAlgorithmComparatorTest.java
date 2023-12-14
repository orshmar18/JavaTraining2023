package test.comperatorTest;

import main.java.comperator.IEncryptionAlgorithmComparator;
import main.java.typesOfEncryption.IEncryptionAlgorithm;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Comparator;

public class IEncryptionAlgorithmComparatorTest {

    @Test
    public void testCompare() {
        // Create mock objects for IEncryptionAlgorithm
        IEncryptionAlgorithm encAlg1 = mock(IEncryptionAlgorithm.class);
        IEncryptionAlgorithm encAlg2 = mock(IEncryptionAlgorithm.class);


        // Define the key strengths for the mock objects
        when(encAlg1.getKeyStrength()).thenReturn(3);
        when(encAlg2.getKeyStrength()).thenReturn(5);

        // Create the comparator
        Comparator<IEncryptionAlgorithm> comparator = new IEncryptionAlgorithmComparator();

        // Test the compare method
        int result = comparator.compare(encAlg1, encAlg2);

        // Assert the result
        assertEquals(5, result); // The result should be the maximum key strength (256)
    }
}